clc 
clearvars
rng("default");
tic

folder = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\MATLAB\Half_System_PSO\result\';
test = 'Test5';
Geneset = 'GeneSet2';
mkdir(fullfile(folder,test,Geneset));
mainfolder = fullfile(folder,test,Geneset);
gene_et = [
0.0433028001	0.5411054953	0.0574149323	0.7106064092	0.5746409504	0.0383169189	0	0
0.061580213	0.6672435294	0.0738621127	0.8395117718	0.7238854849	0.0034604434	0	0
0.0934737342	0.7127546241	0.0983365254	0.9670682724	0.7293161027	0	0.0089606654	0.0231826994
0.0881581473	0.6856711413	0.1106660492	1	0.6161611426	0	0.0109095918	0.0268277779
0.0819662762	0.620645871	0.0998143391	0.9606675877	0.5238846059	0	0.0105183412	0.0256305803
0.0625876467	0.5786193979	0.0853629765	0.9053693654	0.4353681785	0.0234970187	0.0096904138	0.0391697573
0.0437270213	0.4950023958	0.0771877431	0.8141691346	0.3935724993	0.0394928688	0.0066270825	0.0338842103
0.0520612461	0.3809037449	0.0650992707	0.7009255201	0.3300990435	0.0452751723	0.006326684	0.0144704122
0.0617106299	0.3252802131	0.0667184915	0.5839078756	0.2492303206	0.0417949466	0.0145693239	0.0169073029
0.0294229273	0.3015582619	0.0743009875	0.5030560045	0.1899822252	0.0280454906	0.0205245396	0.0176802794
0.0245029871	0.2901687652	0.0703320648	0.4506386762	0.1474575302	0.0178766374	0.0166567267	0.0082858679
0.0300237241	0.2575623473	0.0642566897	0.4206926161	0.1290562947	0.0183008586	0.0138974572	0.0101959623
0.0254591334	0.1995649351	0.0576896869	0.3898416973	0.1219287924	0.0242216384	0	0.0131896892
0.0249030299	0.2191106154	0.0407838494	0.379940272	0.1356775157	0.0305886197	0	0.0089496752
0.0258774931	0.2193421421	0.0307879084	0.381885535	0.1271432695	0.0271889887	0	0.0166596574
0.0247198601	0.1760202922	0.0241534992	0.3828453446	0.1215258189	0.0221496219	0	0.0211568417
0.0243374017	0.2008822921	0.0254320242	0.3846059725	0.1343477031	0.0028142204	0.0101468729	0.0199764957
0.0132805414	0.2069012509	0.0238531008	0.3911312125	0.1316668303	0.0000732679	0.0187968823	0.0190818945
0.0089123086	0.1955337347	0.0211883469	0.3751844519	0.1220137832	0.0012162473	0.0159379685	0.0201288929
0.0103190524	0.1753139896	0.0195845123	0.3598919738	0.1168220191	0.0164977353	0.0104084393	0.0152785573
0.0210344843	0.1712520167	0.0236838519	0.3693156923	0.1155112561	0.017322732	0.0076960613	0.0193859563
0.0278886972	0.1484700927	0.0214968048	0.3288886577	0.0966982549	0.0182737494	0	0.0236069206
0.0249682384	0.1214261745	0.0165116562	0.2869808785	0.0823260217	0.00985893	0	0.0194614222
0.0180077869	0.1098351911	0.0151195659	0.287794885	0.080574186	0.0047814638	0	0.0145466108
0.0151063777	0.106154944	0.0141516968	0.2877934196	0.0844192858	0.0047089286	0	0.0188606254
0.0171827902	0.1159508636	0.0136747227	0.2903621925	0.075628602	0.0100472285	0	0.0189097149
0.0214000911	0.1160614982	0.0173146725	0.2840333105	0.0688754988	0.0078843598	0.0003590128	0.0156002034
0.0259522264	0.1131454353	0.0220939382	0.2825554967	0.0753809565	0.0154558656	0.0009751959	0.0151129718
0.0330181836	0.1223947763	0.0223108113	0.3335880614	0.0814629257	0.0204776482	0.0034435918	0.0182503037
0.0362932592	0.1151134114	0.0244062735	0.3318845825	0.0865345304	0.0195346902	0	0.0144586894
0.0254254301	0.0901092718	0.020646897	0.2607187289	0.0791828283	0.0169534617	0	0.0084653743
0.0199742976	0.07358516	0.0174531488	0.2500435944	0.081462193	0.0178517263	0	0.0067824104
0.0120357196	0.0903730362	0.0146902159	0.2379749042	0.0816622144	0.0146653049	0	0.0021196406
0.0056665402	0.0838895589	0.0125361394	0.2050673551	0.0745816036	0.009406867	0	0.0010513945
0.0019130251	0.0661059718	0.0121368293	0.1633544687	0.0658304844	0.0055339252	0	0.0043931439
0.0053551515	0.0825143202	0.0126460413	0.1417162568	0.0837847858	0.0082968581	0	0.0047294436
0.0050379015	0.0877024209	0.0129515684	0.1630123075	0.0852926393	0.0046261358	0	0.0022332059
0.0106245796	0.0736100711	0.013733337	0.1550048576	0.0896125153	0.0046935423	0	0.0047580181
0.0203516274	0.0537845074	0.0134314733	0.1583114384	0.0915519169	0.0027636656	0	0.0047016018
0.0291254596	0.0527924598	0.0106817286	0.1746787568	0.0981130582	0.0064761506	0	0.0029058053
0.0299218818	0.0343333426	0.0080096479	0.1773823427	0.0932546631	0.0056973127	0.0037564457	0.0060167608
0.0292463516	0.0337362091	0.0078191514	0.1501200861	0.0801968562	0.0106025992	0.0077986363	0.0103161217
0.0264086855	0.0371922565	0.0089972993	0.1673277874	0.0815567086	0.0101226944	0.0095116401	0.00593177
0.0256965214	0.0492228473	0.0048019788	0.150467376	0.0824666961	0.0048811082	0.0099351286	0.0032281841
0.0212813971	0.04011125	0.0090771614	0.0946907141	0.067160297	0.0100142579	0.0104040432	0.0037058909
0.0192540741	0.0352052307	0.0138762095	0.0917262945	0.0596071082	0.0089071798	0.0112033961	0
0.0168494213	0.0237094957	0.0098120385	0.0876144994	0.0616945109	0	0.0041718748	0
0.0113785064	0.0005414499	0.0086719898	0.0479054902	0.0550747553	0	0.0025460599	0
0.0111140093	0	0.012119245	0.0441527079	0.053666546	0	0	0
];

[tp, n] = size(gene_et);    % Returns num of rows and columns 
tp = tp - 1;                % Predict the tp except the firstone
reg = 4;                    % Number of regulator : 4
dt= 6;                      % TimeStamp distance : 6 unit
combination = 1:n;          % As n number of genes 
pop = nchoosek(combination,reg);    % Combination of n_C_reg as regulator 
[pop_size,~]= size(pop);            % population/combination size : 70
dim = reg+3;                        % dimention is regulator + 2(delta_i ,alpha) 
lb = -1*ones(1,reg);                % lower bound of first 4 is -1
ub = 1*ones(1,reg);                 % upper bound of first 4 is 1
lb(1,dim-2) = 0;
ub(1,dim-2) = 1;
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
            mui = position(cur_gene,reg+3,i);
            
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
                 if j ==1
                    di = 0;
                 else
                     di = gene_et(j-1,cur_gene)-next_tp(j-1,cur_gene,i);
                 end

                 next_tp(j,cur_gene,i) = dt*delta_i*var1 + (1-dt*alpha)*gene_et(j,cur_gene)+mui*di;
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
                mui = position(cur_gene,reg+3,i);
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
                    if j ==1
                        di = 0;
                    else
                         di = gene_et(j-1,cur_gene)-next_tp(j-1,cur_gene,i);
                    end

                    next_tp(j,cur_gene,i) = dt*delta_i*var1 + (1-dt*alpha)*gene_et(j,cur_gene)+mui*di;
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
