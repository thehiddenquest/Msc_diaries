clc
clearvars
% rng('default');
gn_st = [ 
0.112353858	0.341759725	0	0.785088843	0.579453682	0.041927075	0	0.020173829
0.158141562	0.798961964	0	0.898880989	0.651318753	0.01122482	0	0.024740877
0.124265817	1	0.032614061	0.973425209	0.674076873	0	0.013855385	0.026616436
0.066642502	0.888943302	0.065751766	0.928606904	0.65223571	0	0.029676559	0.033280378
0.047903106	0.749176358	0.070041182	0.854692754	0.583036678	0	0.027786347	0.02820588
0.038851529	0.551048833	0.073105161	0.765271308	0.482964186	0.029896351	0.01710368	0.026311041
0.042686708	0.39296434	0.082607891	0.662436376	0.389420674	0.025403338	0.007664189	0.016212944
0.044637844	0.303245982	0.072587686	0.541996484	0.264621958	0.018477188	0	0.008996052
0.054247771	0.228870654	0.072266095	0.439483142	0.163000123	0.011242558	0	0.009127927
0.055019743	0.203245982	0.065887497	0.367066663	0.112929173	0.005433106	0	0.012970818
0.042166147	0.170626523	0.056781164	0.318483358	0.107328716	0.00422155	0	0.016104205
0.04107567	0.176863991	0.040153315	0.293687726	0.095683592	0.010968011	0.006319986	0.018862017
0.041701885	0.156198137	0.032625629	0.262222754	0.096074591	0.013378783	0.002639819	0.023889472
0.053940062	0.119272295	0.021854274	0.228896104	0.105186322	0.012190363	0.001662708	0.014689823
0.050613104	0.135538452	0.014243298	0.218592868	0.106318444	0.007235401	0.005920505	0.009530493
0.045578709	0.132807632	0.022411081	0.215036863	0.09751982	0.002676065	0	0.013064133
0.033908906	0.105344418	0.02654857	0.182553136	0.085804516	0	0	0.013690348
0.023152975	0.099968381	0.01581192	0.177643675	0.076463738	0	0.001031095	0.010911713
0.021278187	0.11055619	0.016815251	0.200003085	0.071163279	0	0.000156554	0.016264614
0.01854274	0.097253756	0.01373662	0.183104544	0.066443533	0.00583413	0.001378906	0.018940679
0.026988926	0.085118919	0.006946972	0.173029583	0.066623222	0.012938427	0.000214394	0.016686461
0.027072215	0.07636194	0.009160317	0.171727797	0.062042293	0.010189098	0.007600179	0.020891045
0.021718543	0.084390906	0.015073881	0.178735694	0.049883549	0.008466237	0.009212759	0.021770213
0.014763859	0.087174939	0.024491008	0.169982571	0.037494216	0.008782429	0.007906345	0.023391276
0.010372644	0.084499645	0.026640343	0.148349631	0.04001527	0.008932813	0.006754943	0.022036277
0.008910448	0.078463461	0.030722306	0.143895179	0.039647407	0.004408952	0.006630009	0.022411081
0.006109449	0.075707191	0.028203566	0.145217016	0.044052503	0.006346207	0	0.020250177
0.008311226	0.076080452	0.018217293	0.117369744	0.048614153	0.009607613	0	0.017257149
0.013373384	0.065649196	0.015862048	0.115097017	0.046111608	0.009415584	0.002372983	0.013535336
0.023365055	0.041556128	0.013612456	0.12171006	0.03625027	0.005677577	0.001724404	0.012263627
0.020593362	0.047016226	0.012845112	0.121110066	0.031253663	0.005038252	0	0.011918129
0.032482957	0.061904248	0.014749206	0.107711232	0.035741278	0.005780146	0	0.012367739
0.040173366	0.057205324	0.017792362	0.105510226	0.032230774	0.008958263	0	0.016465126
0.03612148	0.058113798	0.0176173	0.120240923	0.028927723	0.006963939	0	0.013897801
0.032069593	0.078493537	0.018936823	0.130704569	0.032816886	0.008566493	0.002215659	0.013343308
0.031095567	0.076295617	0.018468705	0.124570441	0.034297591	0.0066115	0.001360397	0.011713761
0.019057902	0.07364423	0.015777987	0.113013234	0.028300737	0.001617207	0.004636456	0.012697042
0.012414011	0.060968011	0.011762347	0.078483512	0.026346516	0	0.007216121	0.009369312
0.010982663	0.054386587	0.007018694	0.060940247	0.031320758	0	0.003092513	0.009727921
0.009410957	0.052275812	0.003298424	0.047723417	0.036645125	0	0	0.009505815
0.008507111	0.047836012	0.00378428	0.026454484	0.039766943	0	0	0.002605115
0.010322516	0.047420335	0.009744116	0.039385199	0.040543542	0	0	0.000614647
0.009523553	0.042401364	0.007713545	0.063489064	0.043725514	0	0	0.000142672
0.011705278	0.033163155	0.008106086	0.081016134	0.041549187	0	0	0.000806676
0.017685165	0.031763427	0.010672641	0.0798763	0.035816855	0	0.0000285	0
0.023337292	0.043773329	0.008542586	0.090123238	0.035914027	0.000092544	0.002977604	0.002408459
0.030402258	0.041476694	0.008375235	0.091034797	0.038947929	0.002269643	0.01011275	0.001896382
0.030892742	0.040967702	0.009835889	0.078312305	0.037308357	0.002327483	0.0076264	0.001334948
0.030173212	0.056110991	0.010855415	0.065080051	0.031460345	0	0.006313046	0
];

[tp, n] = size(gn_st);
tp = tp-1;
reg = 4;
dt = 6;
combination = 1:n;
pop = nchoosek(combination,reg);
% pop2 = nchoosek(combination, reg-1);
% if size(pop, 2) > size(pop2, 2)
%     pop2 = [pop2, zeros(size(pop2, 1), size(pop, 2) - size(pop2, 2))];
% end
% pop = [pop; pop2];
% pop3 = nchoosek(combination, reg-2);
% if size(pop, 2) > size(pop3, 2)
%     pop3 = [pop3, zeros(size(pop3, 1), size(pop, 2) - size(pop3, 2))];
% end
% pop = [pop; pop3];
% pop4 = nchoosek(combination, reg-3);
% if size(pop, 2) > size(pop4, 2)
%     pop4 = [pop4, zeros(size(pop4, 1), size(pop, 2) - size(pop4, 2))];
% end
% pop = [pop; pop4];
[pop_size, ~] = size(pop);
next_tp = zeros(tp, n, pop_size);
dim = reg + 3;
lb = -1* ones(1, reg);
ub = 1* ones(1, reg);
lb(1, reg+1:dim) = 0;
ub(1, reg+1:dim) = 1;
% lb(1,dim-1) = 0;
% ub(1,dim-1) = 1;
% lb(1,dim) = 0;
% ub(1,dim) = 0.0001;
max_ite = 5000;
result_matrix = zeros(n,n,10);
all_best_fitness = zeros(10,n);
X = zeros(n,dim,pop_size);
V = zeros(n,dim,pop_size);
g1 = zeros(tp,1);
for cur = 1:10 
    disp("cur "+cur);
    gbest = zeros(n, dim);
    best_combination = zeros(n,reg);
    lbest = zeros(n,dim,pop_size);
    Fitness = zeros(n,pop_size);
    g1(3:6,1) = randn(1,4);
    temp = max(abs(g1));
    g1 = g1/temp;
    for gn = 1:n
        disp("gene "+gn);
        for i = 1:pop_size
            X(gn, :, i) = lb + rand(1, dim) .* (ub - lb); 
            V(gn, :, i) = lb + rand(1, dim) .* (ub - lb);
            % V(gn,:,i) = zeros(1,dim);
        end
        %disp(X(gn,:,1));
        for i = 1:pop_size
            w1(1,:) = X(gn, 1:reg, i);
            alpha = X(gn, reg+1, i);
            pi = X(gn, reg+2, i);
            n1 = X(gn, reg+3, i); 
%             n2 = X(gn, reg+4, i);
% % %             dt = 6;
% % %             di = 0;
            dt = 6*n1;
            curr_exp = 0;
            for j = 1:tp
                var1 = 1;
                for k = 1:reg
                    if (gn_st(j, pop(i, k)) > 0)
                        var1 = var1 * (gn_st(j, pop(i, k)) ^ (w1(1, k)));
                    else
                        var1 = var1 * 99999;
                    end
                end
                next_tp(j, gn, i) = dt*alpha * var1 + (1 - pi*dt)*gn_st(j,gn) + (sqrt(dt*gn_st(j,gn)))*g1(j,1);
%                 R = alpha + pi*gn_st(j,gn);
%                 rf = alpha/R;
%                 if(rand() < rf)
%                     next_tp(j, gn, i) = dt*alpha * var1 + gn_st(j,gn) + n2*alpha*sqrt(dt*var1)*g1(j,1);
%                 else
%                     next_tp(j, gn, i) = (1-pi*dt)*gn_st(j,gn) + n2*pi*sqrt(dt*gn_st(j,gn))*g1(j,1);
%                 end
                
            end
%             temp1 = max(abs(next_tp(:, gn, i)));
%             next_tp(:, gn, i) = next_tp(:, gn, i) / temp1;
            diff = 0;
            for j = 1:tp
                diff = diff + (next_tp(j, gn, i) - gn_st(j+1, gn))^2;
                
            end
            Fitness(gn,i) = (diff / tp) + (alpha+pi)*sum(abs(w1));
        end


        [best_fitness, min_index] = min(Fitness(gn,:));
        lbest(gn,:, :) = X(gn, :, :);
        gbest(gn, :) = lbest(gn, :, min_index);
        best_combination(gn,:) = pop(min_index,:);
        if(best_fitness < 0)
            disp('Err: negetive value')
        end


        c1 = 2;
        c2 = 2;
        wmax = 0.9;
        wmin = 0.1;
%         p = 1;
        for iter = 1:max_ite
%             w = (wmax - wmin)*p + wmin;
%             count = 0;
            w = wmax -  iter* (wmax - wmin) / max_ite;
            % w = rand();
            %rng('shuffle');
            for i = 1:pop_size
                V(gn,:,i) = w .* V(gn, :, i) + c1 .* rand() .* (lbest(gn, :, i) - X(gn, :, i)) + c2 .* rand() .* (gbest(gn, :) - X(gn, :, i));
                X(gn,:,i) = X(gn, :, i) + V(gn,:,i);
%                 if(any(X(gn,:,i) > ub) || any(X(gn,:,i) < lb))
%                     X(gn, :, i) = lb + rand(1, dim) .* (ub - lb);
%                 end
%                 if(any(V(gn,:,i) > ub) || any(V(gn,:,i) < lb))
%                     V(gn,:,i) = lb + rand(1, dim) .* (ub - lb);
% %                 end
                flag_ub = X(gn,:,i) > ub;
                flag_lb = X(gn,:,i) < lb;
                X(gn,reg+1:dim,i) = ((~(flag_ub(reg+1:dim) | flag_lb(reg+1:dim))).*X(gn,reg+1:dim,i)) + ((lb(reg+1:dim) + rand(1, dim-reg) .* (ub(reg+1:dim) - lb(reg+1:dim))).*(flag_ub(reg+1:dim) | flag_lb(reg+1:dim)));
                X(gn,1:reg,i) = ((~(flag_ub(1:reg) | flag_lb(1:reg))).*X(gn,1:reg,i)) + (ub(1:reg).*flag_ub(1:reg) + lb(1:reg).*flag_lb(1:reg));
                flag_ub = V(gn,:,i) > ub;
                flag_lb = V(gn,:,i) < lb;
                V(gn,reg+1:dim,i) = ((~(flag_ub(reg+1:dim) | flag_lb(reg+1:dim))).*V(gn,reg+1:dim,i)) + ((lb(reg+1:dim) + rand(1, dim-reg) .* (ub(reg+1:dim) - lb(reg+1:dim))).*(flag_ub(reg+1:dim) | flag_lb(reg+1:dim)));
                V(gn,1:reg,i) = ((~(flag_ub(1:reg) | flag_lb(1:reg))).*V(gn,1:reg,i)) + (ub(1:reg).*flag_ub(1:reg) + lb(1:reg).*flag_lb(1:reg));
                
%                 V1 = zeros(n,dim,pop_size);
                % X1 = zeros(n,dim,pop_size);
                % V1(gn,:,i) = w .* V(gn, :, i) + c1 .* rand() .* (lbest(gn, :, i) - X(gn, :, i)) + c2 .* rand() .* (gbest(gn, :) - X(gn, :, i));
                % X1(gn,:,i) = X(gn, :, i) + V1(gn,:,i);
                % temp = X1(gn,:,i);
                % L = temp<lb;
                % temp(L) = lb(L);
                % X1(gn,:,i) = temp;
                % temp = V1(gn,:,i);
                % L = temp<lb;
                % temp(L) = lb(L);
                % V1(gn,:,i) = temp;
                % temp = X1(gn,:,i);
                % L = temp>ub;
                % temp(L) = ub(L);
                % X1(gn,:,i) = temp;
                % temp = V1(gn,:,i);
                % L = temp>ub;
                % temp(L) = ub(L);
                % V1(gn,:,i) = temp; 
                % 
                % X(gn,:,i) = X1(gn,:,i);
                % V(gn,:,i) = V1(gn,:,i);
                w1(1,:) = X(gn, 1:reg, i);
                alpha = X(gn, reg+1, i);
                pi = X(gn, reg+2, i);
                n1 = X(gn, reg+3, i);
%                 n2 = X(gn, reg+4, i);
% %                 dt = 6;
% %                 di = 0;
                dt = 6*n1;
                curr_exp = 0;
                for j = 1:tp
                    var1 = 1;
                    for k = 1:reg
                        if (gn_st(j, pop(i, k)) > 0)
                            var1 = var1 * (gn_st(j, pop(i, k)) ^ (w1(1, k)));
                        else
                            var1 = var1 * 99999;
                        end
                    end
                    next_tp(j, gn, i) = dt*alpha * var1 + (1 - pi*dt)*gn_st(j,gn) + (sqrt(dt*gn_st(j,gn)))*g1(j,1);
%                     R = alpha + pi*gn_st(j,gn);
%                     rf = alpha/R;
%                     if(rand() < rf)
%                         next_tp(j, gn, i) = dt*alpha * var1 + gn_st(j,gn) + n2*alpha*sqrt(dt*var1)*g1(j,1);
%                     else
%                         next_tp(j, gn, i) = (1-pi*dt)*gn_st(j,gn) + n2*pi*sqrt(dt*gn_st(j,gn))*g1(j,1);
%                     end
                end
%                 temp1 = max(abs(next_tp(:, gn, i)));
%                 next_tp(:, gn, i) = next_tp(:, gn, i) / temp1;

                diff = 0;
                for j = 1:tp
                    diff = diff + (next_tp(j, gn, i) - gn_st(j+1,gn))^2;
                end
                if (Fitness(gn,i) >= (diff / tp)+ (alpha+pi)*sum(abs(w1)))
                    Fitness(gn,i) = (diff / tp)+ (alpha+pi)*sum(abs(w1));
                    lbest(gn,:, i) = X(gn, :, i);
%                     count = count + 1;
                    %disp("Particle "+i+" is updated");
                end
            end

%             p = count/pop_size;
            if (best_fitness >= min(Fitness(gn,:)))
                [best_fitness, min_index] = min(Fitness(gn,:));
                if(best_fitness < 0)
                    disp('Err: negetive value')
                end
                gbest(gn, :) = lbest(gn, :, min_index);
                best_combination(gn,:) = pop(min_index,:);
                %disp("gbest is updated");
            end
        end
        all_best_fitness(cur,gn) = best_fitness;
        for i = 1:reg
            index = best_combination(gn,i);
            if(index > 0)
                result_matrix(gn,index,cur) = gbest(gn,i);
            end
        end
        disp(best_fitness);
        disp(gbest(gn,:));
        disp(best_combination(gn,:));
        disp(result_matrix(gn,:,cur));
    end
end

final_Matrix = zeros(n,n,1);
final_Matrix_1 = zeros(n,n,10);
for result_set = 1:10
    final_Matrix_1(:,:,result_set) = (abs(result_matrix(:,:,result_set)) > 0);
    final_Matrix(:,:,1) = final_Matrix(:,:,1)+final_Matrix_1(:,:,result_set);
end

actual_network = [  
0	1	0	0	0	0	0	0
0	1	0	1	0	0	0	0
0	1	0	0	0	0	0	0
0	1	0	0	0	0	0	0
0	1	0	0	0	0	0	0
0	1	0	0	0	0	0	0
0	1	0	0	0	0	0	0
0	1	0	0	0	0	0	0

];
     
final_Matrix(:,:,1) = final_Matrix / 10;         

for thrashold = [0.8, 0.9, 1]
    final_Matrix_2 = final_Matrix(:,:,1);
    final_Matrix_3 = final_Matrix_2 >= thrashold;
    disp(final_Matrix_3);
    TP = sum(sum((final_Matrix_3 == 1) & (actual_network == 1)));
    FP = sum(sum((final_Matrix_3 == 1) & (actual_network == 0)));
    FN = sum(sum((final_Matrix_3 == 0) & (actual_network == 1)));
    TN = sum(sum((final_Matrix_3 == 0) & (actual_network == 0)));
    
    accuracy = (TP + TN) ./ (TP + FP + FN + TN);
    precision = TP ./ (TP + FP);
    recall = TP ./ (TP + FN);
    f_score = 2 .* (precision .* recall) ./ (precision + recall);
    disp('Confusion Matrix:');
    disp(['True Positive (TP): ', num2str(TP)]);
    disp(['False Positive (FP): ', num2str(FP)]);
    disp(['False Negative (FN): ', num2str(FN)]);
    disp(['True Negative (TN): ', num2str(TN)]);

    disp(['Accuracy: ', num2str(accuracy)]);
    disp(['Precision: ', num2str(precision)]);
    disp(['Recall: ', num2str(recall)]);
    disp(['F-Score: ', num2str(f_score)]);
    disp('----------------------------------');
end
for run = 1:10
    sum_se = 0;
    for gn = 1:n
        sum_se = sum_se+all_best_fitness(run,gn);
    end
    MSE = sum_se/n;
    disp(['RUN : ', num2str(run),'| MSE : ',num2str(MSE)]);
        
end
