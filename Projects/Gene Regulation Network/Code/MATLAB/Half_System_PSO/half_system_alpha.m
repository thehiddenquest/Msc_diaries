clc 
clearvars
rng("default");
tic

folder = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\MATLAB\Half_System_PSO\result\';
test = 'Test00';
Geneset = 'GeneSet3';
mkdir(fullfile(folder,test,Geneset));
mainfolder = fullfile(folder,test,Geneset);
gene_et = [
0.0797016464	0.5326469734	0.0674898657	0.4436778041	0.5825037593	0.0210263407	0	0.0204332129
0.1056733584	0.860238027	0.092771839	0.7344680103	0.8411336106	0.0092849445	0.0030479958	0.0209550704
0.0925675306	0.8532226477	0.1255649166	0.8685972485	1	0.0056327343	0.0033006097	0.0214262467
0.0812450457	0.7280845615	0.1250169267	0.840448623	0.8888095233	0	0.0073844015	0.0219884906
0.0378421868	0.6200196865	0.0945900251	0.7739153639	0.7282967885	0.0048622225	0.0004410844	0.0248820673
0.033758395	0.4788837792	0.0839366897	0.6686078599	0.5369295302	0.0128516301	0	0.0228405674
0.0399470382	0.3377225318	0.072028204	0.6005414964	0.3320470352	0.0111736091	0	0.0200515206
0.035164005	0.2471133523	0.0625278647	0.5160283085	0.2449887036	0.0088272303	0	0.0177827473
0.0385208391	0.2000258157	0.0514104801	0.4712491397	0.1891642124	0.0117897017	0	0.0172363412
0.0638899206	0.147059266	0.04196003	0.4145424799	0.1650154379	0.0089190899	0	0.0156264229
0.0849344748	0.1520466076	0.0269916701	0.3645645341	0.1631719112	0	0	0.0141740913
0.0634385416	0.1691087324	0.0244821614	0.3483560699	0.1546646056	0	0	0.011181528
0.0475460426	0.1168350891	0.021822985	0.3133591543	0.1481821702	0	0	0.007054974
0.0608728085	0.0930783015	0.0158489448	0.2838967783	0.1367472365	0	0	0.0041416
0.0645178916	0.1049519439	0.0168134704	0.2322392276	0.1240143902	0.0015101398	0	0.0047173061
0.0389785532	0.0756202303	0.0250063945	0.235416302	0.1010138605	0.0046998845	0	0.000547198
0.0273060516	0.0653097845	0.020706416	0.2282092846	0.0827021287	0.0064768922	0	0.0008528687
0.0162417237	0.0743516179	0.0150673465	0.2224189636	0.0624288781	0.0021428622	0	0.0012163475
0.00964288	0.0978795483	0.0212678681	0.2440431835	0.0692336139	0.0030155282	0	0.0037622832
0.0135247391	0.1149678056	0.0280963607	0.2448295331	0.0640150396	0.008851779	0.012874595	0
0.0289745699	0.1110867384	0.0228484863	0.2422052	0.0541044998	0.0072291904	0.0110849171	0.0043577867
0.0307864209	0.1060297102	0.0209859542	0.2152950949	0.0509757309	0.0062393243	0.0109875142	0.0096333773
0.0301014336	0.0973648179	0.0186918404	0.2139845121	0.0504324923	0.0072268148	0	0.010308862
0.0230052811	0.0858997923	0.0092722742	0.2095301145	0.044378471	0.0075111043	0	0.0127328462
0.017260098	0.0705362778	0.0077771803	0.1959816186	0.0451442314	0.0070969443	0	0.0124073782
0.0152898684	0.0630307167	0.012771649	0.1837381622	0.0471152529	0.0045518005	0	0.0100562482
0.0154276578	0.0709456864	0.0234368628	0.1835655295	0.0521532756	0.0078516183	0	0.0086767707
0.0106913463	0.0815895191	0.0240735447	0.1709300861	0.050027835	0.0096460476	0.0001282867	0.0089800657
0.0163224968	0.072687059	0.026023185	0.1646060293	0.0422340249	0.0134542607	0	0.0073281771
0.0227518754	0.069418125	0.029957309	0.1725811829	0.0397261001	0.0123368998	0	0.0064531354
0.0277946495	0.0727068563	0.0251623974	0.1872509991	0.0411950614	0.0114088013	0	0.0082523161
0.025128346	0.0626941622	0.0212013491	0.1783319092	0.0343626093	0.004281765	0	0.0050926633
0.0251473515	0.0581130617	0.0207863972	0.1692805732	0.0381668629	0	0	0.0013287963
0.0146888217	0.0604950756	0.0188003297	0.1569349626	0.0491234933	0	0	0
0.0146476433	0.059035617	0.0155234769	0.141461375	0.0492034745	0.0035358019	0	0
0.0168269326	0.0566441004	0.0146531865	0.1337475461	0.0388938206	0.0038565185	0	0
0.0205432861	0.0563914865	0.0142516968	0.1357835028	0.0403984172	0.0029070389	0.003741694	0
0.0197878202	0.0551743471	0.0153468847	0.1382138221	0.0483601086	0.0011229042	0.0062789189	0
0.0226679347	0.0607579841	0.015137825	0.1300929603	0.0495709128	0	0	0.000170257
0.0228603647	0.0736056547	0.0147418785	0.125222027	0.0541116268	0	0	0
0.0169377976	0.0759971713	0.0117303098	0.1056377232	0.0550832794	0	0	0
0.0145431134	0.0648734516	0.0060152186	0.1009164577	0.0563685216	0	0	0
0.0170724194	0.0492969178	0.0044195544	0.1085154621	0.043490759	0	0	0
0.0162480589	0.0419481516	0.004326111	0.0982351083	0.0359954925	0.0000118784	0	0.0038850267
0.0149224301	0.0403714928	0.0071911796	0.1004096462	0.0345835475	0	0.0022378894	0.0050190173
0.0162068804	0.050989193	0.0115711393	0.1044443406	0.0359052167	0.0018403591	0.0048036224	0.0071856363
0.0136918285	0.0558838832	0.012061321	0.0968928498	0.0392430454	0.0017580023	0.0046254465	0.005043566
0.0073590609	0.0554475501	0.011795245	0.08897788	0.0409242341	0.0020090323	0	0.0003666464
0.0038343455	0.0506692683	0.0092596039	0.0875192133	0.0407405149	0.0027969658	0	0
];

[tp, n] = size(gene_et);    % Returns num of rows and columns 
tp = tp - 1;                % Predict the tp except the firstone
reg = 4;                    % Number of regulator : 4
dt= 6;                      % TimeStamp distance : 6 unit
combination = 1:n;          % As n number of genes 
pop = nchoosek(combination,reg);    % Combination of n_C_reg as regulator 
[pop_size,~]= size(pop);            % population/combination size : 70
dim = reg+2;                        % dimention is regulator + 2(delta_i ,alpha) 
lb = -1*ones(1,reg);                % lower bound of first 4 is -1
ub = 1*ones(1,reg);                 % upper bound of first 4 is 1
lb(1,dim-1) = 0;                    % lower bound of delta_i is 0
ub(1,dim-1) = 1;                    % upper bound of delta_i is 1
lb(1,dim) = 0;                      % lower bound of alpha is 0
ub(1,dim) = 1;                      % upper bound of alpha is 1
max_iter = 5000;                    % Maximum iteration 5000 times
max_repetition = 10;                % Repeat 10 times
next_tp = zeros(tp, n, pop_size);   % To store population size of timepoint
result_matrix = zeros(n,n,max_repetition); % To store result
all_global_best_fitness = zeros(max_repetition,n); % To store all best fitness
position = zeros(n,dim,pop_size);   % Position matrix
velocity = zeros(n,dim,pop_size);   % Velocity matrix

for cur = 1:max_repetition
    % For each repetition
    disp("Current repetition: "+ cur);
    gbest = zeros(n,dim);           % Global best position
    lbest = zeros(n,dim,pop_size);  % Local best position 
    regulator_combination = zeros(n,reg); % Best regulator combination
    Fitness = zeros(n,pop_size);    % Fitness of population
    for cur_gene = 1:n
        % For each gene
        disp("Current gene : "+cur_gene);
        for i = 1:pop_size
            % Intialize position and velocity from a range for PSO
            position(cur_gene,:,i) =  lb + rand(1, dim) .* (ub - lb);  
            velocity(cur_gene,:,i) =  lb + rand(1, dim) .* (ub - lb); 
        end
       % disp(position);
        for i = 1:pop_size
            % Separate sections from position vector
            weight(1,:) = position(cur_gene,1:reg,i);  
            delta_i = position(cur_gene,reg+1,i);
            alpha = position(cur_gene,reg+2,i);
            
            % Half System :: 
             for j = 1:tp
                 var1 = 1;
                 for k = 1:reg 
                    if(gene_et(j,pop(i,k))>0)
                        var1 = var1 * (gene_et(j,pop(i,k)) .^ weight(1, k));
                    else
                        var1 = var1*99999999;
                    end
                 end
                 next_tp(j,cur_gene,i) = dt*delta_i*var1 + (1-dt*alpha)*gene_et(j,cur_gene);
             end
            diff = 0;
            for j = 1:tp
                diff = diff + (next_tp(j, cur_gene, i) - gene_et(j+1, cur_gene))^2;
            end
            Fitness(cur_gene,i) = diff / tp;
        end
        [best_fitness,min_index] = min(Fitness(cur_gene,:));
        lbest(cur_gene,:,:) = position(cur_gene,:,:);
        gbest(cur_gene,:) = position(cur_gene,:,min_index);
        best_combination = pop(min_index,:);




        % Update PSO
        c = 2;
        inertia_max = 0.9;
        inertia_min = 0.2;
        for iter = 1:max_iter
            inertia = inertia_max - iter*(inertia_max-inertia_min)/max_iter;
            for i = 1:pop_size
                velocity(cur_gene,:,i) = inertia.*velocity(cur_gene,:,i)+c*rand().*(lbest(cur_gene,:,i)-position(cur_gene,:,i))+c*rand().*(gbest(cur_gene,:)-position(cur_gene,:,i));
                position(cur_gene,:,i) = position(cur_gene,:,i)+velocity(cur_gene,:,i);
            end
            for i = 1:pop_size

                flag_ub = position(cur_gene,:,i) > ub;
                flag_lb = position(cur_gene,:,i)  < lb;
                position(cur_gene,:,i)  = ((~(flag_ub | flag_lb)).*position(cur_gene,:,i)) + (ub.*flag_ub + lb.*flag_lb);
                flag_ub = velocity(cur_gene,:,i) > ub;
                flag_lb = velocity(cur_gene,:,i) < lb;
                velocity(cur_gene,:,i) = ((~(flag_ub | flag_lb)).*velocity(cur_gene,:,i)) + (ub.*flag_ub + lb.*flag_lb);

            end

            for i= 1:pop_size
                
                % Separate sections from position vector
                weight(1,:) = position(cur_gene,1:reg,i);  
                delta_i = position(cur_gene,reg+1,i);
                alpha = position(cur_gene,reg+2,i);
            
                % Half System :: 
                 for j = 1:tp
                     var1 = 1;
                     for k = 1:reg
                        if(gene_et(j,pop(i,k))>0)
                            var1 = var1 * (gene_et(j,pop(i,k)) .^ weight(1, k));
                        else
                            var1 = var1*99999999;
                        end
                     end
                     next_tp(j,cur_gene,i) = dt*delta_i*var1 + (1-dt*alpha)*gene_et(j,cur_gene);
                 end
                diff = 0;
                for j = 1:tp
                    diff = diff + (next_tp(j, cur_gene, i) - gene_et(j+1, cur_gene))^2;
                end
                if (Fitness(cur_gene,i) >= diff/ tp)
                    Fitness(cur_gene,i) = diff / tp;
                    lbest(cur_gene,:, i) = position(cur_gene, :, i);
                end

            end
            if (best_fitness >= min(Fitness(cur_gene,:)))
                [best_fitness, min_index] = min(Fitness(cur_gene,:));
                gbest(cur_gene, :) = lbest(cur_gene, :, min_index);
                best_combination(cur_gene,:) = pop(min_index,:);
            end

        end
         all_global_best_fitness(cur,cur_gene) = best_fitness;
        for i = 1:reg
            index = best_combination(cur_gene,i);
            if(index > 0)
                result_matrix(cur_gene,index,cur) = gbest(cur_gene,i);
            end
        end

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
    output_file = ['output_thr_', num2str(thrashold), '.txt'];
    diary(fullfile(mainfolder,output_file));  % Start saving output to a file
    diary on;
    final_Matrix_2 = final_Matrix(:,:,1);
    final_Matrix_3 = final_Matrix_2 >= thrashold;
    %disp(final_Matrix_3);
    TP = sum(sum((final_Matrix_3 == 1) & (actual_network == 1)));
    FP = sum(sum((final_Matrix_3 == 1) & (actual_network == 0)));
    FN = sum(sum((final_Matrix_3 == 0) & (actual_network == 1)));
    TN = sum(sum((final_Matrix_3 == 0) & (actual_network == 0)));
    
    accuracy = (TP + TN) ./ (TP + FP + FN + TN);
    precision = TP ./ (TP + FP);
    recall = TP ./ (TP + FN);
    f_score = 2 .* (precision .* recall) ./ (precision + recall);
    disp(['Confusion Matrix:[', num2str(thrashold),']']);
    disp(['True Positive (TP): ', num2str(TP)]);
    disp(['False Positive (FP): ', num2str(FP)]);
    disp(['False Negative (FN): ', num2str(FN)]);
    disp(['True Negative (TN): ', num2str(TN)]);

    disp(['Accuracy: ', num2str(accuracy)]);
    disp(['Precision: ', num2str(precision)]);
    disp(['Recall: ', num2str(recall)]);
    disp(['F-Score: ', num2str(f_score)]);
    disp('----------------------------------');

    toc
    diary off;
    filename_obj = ['final_Matrix_',num2str(thrashold),'.mat'];
    save(fullfile(mainfolder, filename_obj), 'final_Matrix_3');
    
end
for run = 1:10
    sum_se = 0;
    for gn = 1:n
        sum_se = sum_se+all_global_best_fitness(run,gn);
    end
    MSE = sum_se/n;
    disp(['RUN : ', num2str(run),'| MSE : ',num2str(MSE)]);
        
end
