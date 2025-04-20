clc
clearvars
tic

rng ('default')
mainfolder = ('F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\Reference code\Grn Result\');
mkdir(mainfolder)
for loop = 1:10
    clearvars -except loop
    
    
    gn_st_full = readmatrix('gene_data.csv');  % Read the full matrix
    gn_st = gn_st_full(:, 2:end);              % Remove the first column
    [tp,n] = size(gn_st);
    tp = tp - 1;
    next_tp = zeros(tp,n);
    dt = 6;
    reg = 4;
    combination = 1:n;
    pop = nchoosek(combination,reg);
    [pop_size,~] = size(pop);
    dim = reg + 2;
    lb = -1*ones(1,reg);
    ub = 1*ones(1,reg);
    lb(1,dim-1) = 0;
    ub(1,dim-1) = 1;
    lb(1,dim) = 0;
    ub(1,dim) = 1;
    max_ite = 5000;
    gbest = zeros(n,dim);
    
    for gn = 1:n
        for i = 1:pop_size
            X(gn,:,i) = lb + rand(1,dim).*(ub - lb);
            V(gn,:,i) = lb + rand(1,dim).*(ub - lb);
        end
        for i = 1:pop_size
            w1(1,:) = X(gn,1:reg,i);
            alpha = X(gn,reg+1,i);
            pi = X(gn,reg+2,i);
            for j = 1:tp
                var1 = 1;
                for k = 1:reg
                    if(gn_st(j,pop(i,k))>0)
                    var1 = var1*(gn_st(j,pop(i,k))^(w1(1,k)));
                    end
                end
                next_tp(j,gn,i) = dt*alpha*var1 + (1 - dt*pi)*gn_st(j,gn);
            end
            diff = 0;
            for j = 1:tp
                diff = diff + (next_tp(j,gn,i) - gn_st(j+1,gn))^2;
            end
            Fitness(1,i) = diff/tp;
        end
        c1 = 2;
        c2 = 2;
        wmax = 0.9;
        wmin = 0.1;
        [min_fit,ind] = min(Fitness(:));   %min fitness value with index
        pbest = X;
        gbest(gn,:) = X(gn,:,ind);
        gmse(gn,1) = min_fit;
        gpop(gn,:) = pop(ind,:);
        %%Update of the position and velocity
        for ite = 1:max_ite
            w = wmax - ite*(wmax-wmin)/max_ite;
            %w = rand;
            for i = 1:pop_size
                V1(gn,:,i) = w*V(gn,:,i) + rand*c1*(pbest(gn,:,i) - X(gn,:,i)) + rand*c2*(gbest(gn,:) - X(gn,:,i));
                X1(gn,:,i) = V1(gn,:,i) + X(gn,:,i);
            end
            for i = 1:pop_size
                temp = X1(gn,:,i);
                L = temp<lb;
                temp(L) = lb(L);
                X1(gn,:,i) = temp;
                temp = V1(gn,:,i);
                L = temp<lb;
                temp(L) = lb(L);
                V1(gn,:,i) = temp;
                temp = X1(gn,:,i);
                L = temp>ub;
                temp(L) = ub(L);
                X1(gn,:,i) = temp;
                temp = V1(gn,:,i);
                L = temp>ub;
                temp(L) = ub(L);
                V1(gn,:,i) = temp;
            end
            X = X1;
            V = V1;
            for i = 1:pop_size
                w1(1,:) = X(gn,1:reg,i);
                alpha = X(gn,reg+1,i);
                pi = X(gn,reg+2,i);
                for j = 1:tp
                    var1 = 1;
                    for k = 1:reg
                        if(gn_st(j,k)>0)
                        var1 = var1*(gn_st(j,pop(i,k))^(w1(1,k)));
                        end
                    end
                    next_tp(j,gn,i) = dt*alpha*var1 + (1 - dt*pi)*gn_st(j,gn);
                end
                diff = 0;
                for j = 1:tp
                    diff = diff + (next_tp(j,gn,i) - gn_st(j+1,gn))^2;
                end
                Fitness1(1,i) = diff/tp;
            end
            [min_fit1,ind1] = min(Fitness1(:));
            for i = 1:pop_size
                if(Fitness1(1,i) <= Fitness(1,i))
                    pbest(gn,:,i) = X(gn,:,i);
                    Fitness(1,i) = Fitness1(1,i);
                end
                if(min_fit1 <= gmse(gn,1))
                    min_fit = min_fit1;
                    ind = ind1;
                    gmse(gn,1) = min_fit;
                    gbest(gn,:) = X(gn,:,ind);
                    gpop(gn,:) = pop(ind,:);
                end
            end
        end
        
        
    end
    g_alpha = gbest(:,reg+1);  %% storing the alpha
    g_pi = gbest(:,reg+2);
    gbest_pos = zeros(n,n);
    for i = 1:n
        for j = 1:reg
            var1 = gpop(i,j);
            var2 = gbest(i,j);
            gbest_pos(i,var1) = var2;
        end
    end
    filename = sprintf('10G09ID02E%0.2d.mat',loop);
    save(fullfile(mainfolder,filename));
end
toc