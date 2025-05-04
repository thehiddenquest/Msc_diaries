clc         % Clear console
clearvars   % Clear variable
tic         % Start recording time
%% Basic Information 

folder  = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\MATLAB\Stochastic_Half_System_PSO\Results';
test_number = 'Test 2';  
gene_set_number = 'GeneSet4';   % GeneSet1 || GeneSet2 || GeneSet3 || GeneSet4
mkdir(fullfile(folder,test_number,gene_set_number));
main_folder = fullfile(folder,test_number,gene_set_number);

%% Gene Dataset

gn_st = [
0.0223877964	0.4160059625	0.0714768934	0.6595970064	0.6478754416	0.0007942563	0.0000000000	0.0032528787
0.0332401335	0.5874122943	0.1016132091	0.8795998287	0.9052863687	0.0052299198	0.0000000000	0.0095778226
0.0489850189	0.7005208310	0.0914845669	1.0000000000	0.9576892036	0.0129718240	0.0000000000	0.0177029631
0.0442252142	0.6370257503	0.0895957275	0.9427650349	0.8396374028	0.0038808748	0.0000000000	0.0188994192
0.0373304874	0.5377472403	0.0969654205	0.8646650761	0.6859618173	0.0021238969	0.0000000000	0.0212499945
0.0432016335	0.4632300345	0.0701984097	0.7032286231	0.5398689323	0.0000000000	0.0000000000	0.0219348807
0.0467570440	0.3672793744	0.0596697728	0.5864879406	0.4333455346	0.0000000000	0.0000000000	0.0203304918
0.0405820430	0.3097714252	0.0615431769	0.5203534241	0.3212508766	0.0033679819	0.0000000000	0.0236058619
0.0453334686	0.2548200024	0.0570113736	0.4629103032	0.2075879922	0.0154701371	0.0000000000	0.0215856020
0.0428722001	0.2010544513	0.0461731487	0.3819389379	0.1350513554	0.0270810088	0.0000000000	0.0196597178
0.0365053605	0.1736889920	0.0341953580	0.3388475565	0.1200530974	0.0263890665	0.0000000000	0.0142167910
0.0374574978	0.1509602960	0.0332895265	0.2906991307	0.1084431077	0.0245972490	0.0000000000	0.0148015682
0.0366076744	0.1367770217	0.0338690116	0.2675744092	0.1224671779	0.0192972089	0.0000000000	0.0131491094
0.0384277165	0.1357318315	0.0346218132	0.2741141242	0.1323457683	0.0121180315	0.0000000000	0.0131261769
0.0363201369	0.1333349504	0.0332101449	0.2834467461	0.1193135262	0.0086702271	0.0000000000	0.0142675069
0.0345000948	0.1182577518	0.0336996644	0.2610673287	0.1079138975	0.0072118120	0.0000000000	0.0148209726
0.0269822230	0.1182529007	0.0296736978	0.2731580176	0.1028956618	0.0078151116	0.0043055660	0.0135720365
0.0196623639	0.1224217540	0.0257707726	0.3056224172	0.0982787438	0.0149347528	0.0044784413	0.0166560090
0.0186828840	0.1064837069	0.0273451729	0.2740664953	0.0804381860	0.0151874507	0.0026694244	0.0160319819
0.0252084868	0.0859781348	0.0273425269	0.2539371033	0.0754049561	0.0207741463	0.0000000000	0.0152430177
0.0303744603	0.0887220897	0.0254435443	0.2738067412	0.0866643440	0.0168407915	0.0000000000	0.0150891058
0.0330284495	0.0852372405	0.0198925703	0.2334721040	0.0759407814	0.0143856972	0.0000000000	0.0117003965
0.0220499839	0.0709353349	0.0178083642	0.2024965491	0.0562457829	0.0107090094	0.0000000000	0.0102874052
0.0188548773	0.0823636291	0.0150723474	0.1892896676	0.0555644247	0.0075699109	0.0000000000	0.0108642444
0.0176950250	0.0877968538	0.0118701847	0.2046469065	0.0526524456	0.0069666112	0.0000000000	0.0131270590
0.0255996613	0.0764426490	0.0091429882	0.1939886131	0.0407209607	0.0083866586	0.0000000000	0.0122648873
0.0251873184	0.0626593694	0.0091160867	0.1654006783	0.0311603375	0.0067606603	0.0000000000	0.0111495769
0.0233509590	0.0716740242	0.0088550096	0.1627974051	0.0335073847	0.0110071311	0.0000000000	0.0101881783
0.0136483310	0.0737948340	0.0139614470	0.1917800426	0.0333464166	0.0134639894	0.0013578652	0.0049820730
0.0134203296	0.0820416929	0.0147248327	0.1925002095	0.0423892958	0.0148540482	0.0000057331	0.0040630113
0.0100541117	0.0841801431	0.0145184408	0.1485373953	0.0509192822	0.0169673610	0.0000000000	0.0014368057
0.0144403823	0.0827036467	0.0147032233	0.1399999118	0.0519605033	0.0183261081	0.0000000000	0.0039532002
0.0184835482	0.0703201281	0.0133127235	0.1472518555	0.0489369490	0.0178767205	0.0000000000	0.0038433891
0.0193646832	0.0478944049	0.0119482432	0.1301319056	0.0454922316	0.0129705009	0.0000000000	0.0032321513
0.0172010955	0.0453197973	0.0115998465	0.0943114314	0.0367893699	0.0096197184	0.0000000000	0.0035029305
0.0165206194	0.0472236310	0.0101079148	0.1110300635	0.0326818168	0.0112007338	0.0000000000	0.0028105472
0.0145391682	0.0467460188	0.0090944772	0.1300692824	0.0355324957	0.0079328609	0.0000000000	0.0015682262
0.0137131593	0.0537567309	0.0087628389	0.1370416268	0.0438146353	0.0084492818	0.0000000000	0.0032775752
0.0128465776	0.0541205629	0.0070653972	0.1144227419	0.0365181497	0.0065256027	0.0000000000	0.0024647965
0.0124770124	0.0577765233	0.0064435752	0.1083853356	0.0346006447	0.0077926202	0.0006050637	0.0024308388
0.0122953169	0.0554352092	0.0081807076	0.0821925178	0.0319651780	0.0052316838	0.0000000000	0.0007479504
0.0120033693	0.0492077282	0.0059769882	0.0577072850	0.0199984124	0.0061573607	0.0000000000	0.0023646876
0.0143402733	0.0505479531	0.0058777613	0.0567855773	0.0154644040	0.0066168915	0.0000000000	0.0000000000
0.0214563865	0.0577156642	0.0079377120	0.0803261699	0.0149951710	0.0077105926	0.0000000000	0.0000000000
0.0208649941	0.0508306395	0.0086803703	0.0909403624	0.0153603260	0.0069661702	0.0034134058	0.0000000000
0.0204367748	0.0445656728	0.0076378262	0.0891009160	0.0149294607	0.0060008026	0.0061375153	0.0000000000
0.0144888932	0.0443151800	0.0076845731	0.0848570912	0.0155517237	0.0011091364	0.0033842992	0.0000000000
0.0073710160	0.0392651916	0.0060987065	0.0397948428	0.0153338655	0.0000000000	0.0015439708	0.0000000000
0.0072413595	0.0349904963	0.0055447998	0.0279789021	0.0138459028	0.0000000000	0.0000000000	0.0000000000

];

%% Global Variable Declaration

[tp,n] = size(gn_st);                   % Return column(timepoint) and row(number of gene) 
tp = tp - 1;                            % Predict the tp except the first one
reg = 4;                                % Number of regulator : 4
dt = 6;                                 % TimeStamp distance : 6 unit
combination = 1:n;                      % Create combinaton of n gene : 8 gene
pop = nchoosek(combination,reg);        % Create population of 1:8 * 4
[pop_size,~] = size(pop);               % Population_size: 70
dim = reg + 3;                          % Dimension of particle ( 4 + alpha, delta_i, noise1)
lb = -1* ones(1, reg);                  % Lower bound of first 4 is -1
ub = 1*ones(1,reg);                     % Upper bound of first 4 is 1
lb(1, reg+1:dim) = 0;                   % All other lower bound is 0
ub(1, reg+1:dim) = 1;                   % All other upper bound is 1
max_iter = 5000;                        % Maximum iteration 5000 times
max_repetition = 10;                    % Repeat 10 times
next_tp = zeros(tp, n, pop_size);       % To store population size of timepoint
result_matrix = zeros(n,n,max_repetition); % To store result
all_global_best_fitness = zeros(max_repetition,n); % To store all best fitness
position = zeros(n,dim,pop_size);       % Position matrix
velocity = zeros(n,dim,pop_size);       % Velocity matrix
gaussian_white_noise = zeros(tp,1);     % To store gaussian_white_noise

%% Calculation

for cur = 1:max_repetition
    disp("Current repetition: "+ cur);
    gbest = zeros(n,dim);                                % Global best position
    lbest = zeros(n,dim,pop_size);                       % Local best position 
    regulator_combination = zeros(n,reg);                % Best regulator combination
    Fitness = zeros(n,pop_size);                         % Fitness of population
    gaussian_white_noise(3:6,1) = randn(1,4);            % Noise in 3 to 6 time point, Standard normal
% For Test 1    temp = max(abs(gaussian_white_noise));               % Normalization of noise
% For Test 1    gaussian_white_noise = gaussian_white_noise/temp;
        
    for cur_gene = 1:n
        disp("Current gene : "+cur_gene);
        for i = 1:pop_size
            % Intialize position and velocity from a range for PSO
            position(cur_gene,:,i) =  lb + rand(1, dim) .* (ub - lb);      
            velocity(cur_gene,:,i) =  lb + rand(1, dim) .* (ub - lb); 
        end % End of population(Intialization)
        
        
        
        
        %% Initial Half System ::
        for i = 1:pop_size
            % Separation of position vector
            weight(1,:) = position(cur_gene,1:reg,i);  
            delta_i = position(cur_gene,reg+1,i);
            alpha = position(cur_gene,reg+2,i);
            noise_strength = position(cur_gene,reg+3,i);
            dt = 6 * noise_strength;

            for j = 1:tp
                prod = 1;
                for k = 1:reg
                     prod = prod * (gn_st(j,pop(i,k)) .^ weight(1, k)); % For Test 2
% Test 1                    % if(gn_st(j , pop(i,k) ) >0)
                    %     prod = prod * (gn_st(j,pop(i,k)) .^ weight(1, k));
                    % else
                    %     prod = prod * 99999;               % If gn_st == 0 then multiply with 99999
                    % end % End of Gene value check
                end % End of Each regulator
% For Test 1                next_tp(j,cur_gene,i) = dt*delta_i*prod + (1-alpha*dt)*gn_st(j,cur_gene) + (sqrt(dt*gn_st(j,cur_gene)))*gaussian_white_noise(j,1);
                next_tp(j, cur_gene, i) = dt*delta_i* prod + (1 - alpha*dt)*gn_st(j,cur_gene) + dt*(sqrt(alpha*gn_st(j,cur_gene)))*gaussian_white_noise(j,1); % For Test 2
            end % End of Each time point ( Next Gene Expression Calculation) 
            diff = 0;
            for j  = 1 :tp
                diff = diff + (next_tp(j, cur_gene, i) - gn_st(j+1, cur_gene))^2;
            end % End of Each time point ( Difference calculation)
            Fitness(cur_gene,i) = (diff / tp);
% For Test 1            Fitness(cur_gene,i) = (diff / tp) + (alpha+delta_i)*sum(abs(weight));   % L1 Regularization
        end %% End of population(Half System)
        
        % End of Half System 
        [best_fitness,min_index] = min(Fitness(cur_gene,:));
        lbest(cur_gene,:,:) = position(cur_gene,:,:);
        gbest(cur_gene,:) = position(cur_gene,:,min_index);
        regulator_combination(cur_gene,:) = pop(min_index,:);


         %% Update position and velocity ( PSO ) 
         c = 2;
         inertia_max = 0.9;
         inertia_min = 0.1;
         for iter = 1:max_iter
             inertia = inertia_max - iter*(inertia_max-inertia_min)/max_iter;
             for i = 1:pop_size
                velocity(cur_gene,:,i) = inertia.*velocity(cur_gene,:,i)+c*rand().*(lbest(cur_gene,:,i)-position(cur_gene,:,i))+c*rand().*(gbest(cur_gene,:)-position(cur_gene,:,i));
                position(cur_gene,:,i) = position(cur_gene,:,i)+velocity(cur_gene,:,i);
             end % End of population (Update position and velocity)

             for i = 1:pop_size
                flag_ub = position(cur_gene,:,i) > ub;
                flag_lb = position(cur_gene,:,i) < lb;
                position(cur_gene,reg+1:dim,i) = ((~(flag_ub(reg+1:dim) | flag_lb(reg+1:dim))).*position(cur_gene,reg+1:dim,i)) + ((lb(reg+1:dim) + rand(1, dim-reg) .* (ub(reg+1:dim) - lb(reg+1:dim))).*(flag_ub(reg+1:dim) | flag_lb(reg+1:dim)));
                position(cur_gene,1:reg,i) = ((~(flag_ub(1:reg) | flag_lb(1:reg))).*position(cur_gene,1:reg,i)) + (ub(1:reg).*flag_ub(1:reg) + lb(1:reg).*flag_lb(1:reg));
                flag_ub = velocity(cur_gene,:,i) > ub;
                flag_lb = velocity(cur_gene,:,i) < lb;
                velocity(cur_gene,reg+1:dim,i) = ((~(flag_ub(reg+1:dim) | flag_lb(reg+1:dim))).*velocity(cur_gene,reg+1:dim,i)) + ((lb(reg+1:dim) + rand(1, dim-reg) .* (ub(reg+1:dim) - lb(reg+1:dim))).*(flag_ub(reg+1:dim) | flag_lb(reg+1:dim)));
                velocity(cur_gene,1:reg,i) = ((~(flag_ub(1:reg) | flag_lb(1:reg))).*velocity(cur_gene,1:reg,i)) + (ub(1:reg).*flag_ub(1:reg) + lb(1:reg).*flag_lb(1:reg));
             end % End of population (Reinitialization)
             
            %% Updation Half System ::
            for i = 1:pop_size
                % Separation of position vector
                weight(1,:) = position(cur_gene,1:reg,i);  
                delta_i = position(cur_gene,reg+1,i);
                alpha = position(cur_gene,reg+2,i);
                noise_strength = position(cur_gene,reg+3,i);
                dt = 6 * noise_strength;
    
                for j = 1:tp
                    prod = 1;
                    for k = 1:reg
                        prod = prod * (gn_st(j,pop(i,k)) .^ weight(1, k));
% For Test 1                        % if(gn_st(j , pop(i,k) ) >0)
                        %     prod = prod * (gn_st(j,pop(i,k)) .^ weight(1, k));
                        % else
                        %     prod = prod * 99999;               % If gn_st == 0 then multiply with 99999
                        % end % End of Gene value check
                    end % End of Each regulator
                    next_tp(j, cur_gene, i) = dt*delta_i* prod + (1 - alpha*dt)*gn_st(j,cur_gene) + dt*(sqrt(alpha*gn_st(j,cur_gene)))*gaussian_white_noise(j,1); % For Test 2
% For Test 1                    next_tp(j,cur_gene,i) = dt*delta_i*prod + (1-alpha*dt)*gn_st(j,cur_gene) + (sqrt(dt*gn_st(j,cur_gene)))*gaussian_white_noise(j,1);
                end % End of Each time point ( Next Gene Expression Calculation) 
                diff = 0;
                for j  = 1 :tp
                    diff = diff + (next_tp(j, cur_gene, i) - gn_st(j+1, cur_gene))^2;
                end % End of Each time point ( Difference calculation)
                
 % For Test 1               % if(Fitness(cur_gene,i) >= (diff / tp) + (alpha+delta_i)*sum(abs(weight)))
                %     Fitness(cur_gene,i) = (diff / tp) + (alpha+delta_i)*sum(abs(weight)); % Update fitness 
                %     lbest(cur_gene,:, i) = position(cur_gene, :, i);                      % Update local best position
                % end % If change in fitness occurs 
                
                % For Test 2
                 if(Fitness(cur_gene,i) >= (diff / tp) )
                     Fitness(cur_gene,i) = (diff / tp) ; % Update fitness 
                     lbest(cur_gene,:, i) = position(cur_gene, :, i);                      % Update local best position
                 end % If change in fitness occurs 
            end %% End of population(Half System)
            if (best_fitness >= min(Fitness(cur_gene,:)))
                [best_fitness, min_index] = min(Fitness(cur_gene,:));
                gbest(cur_gene, :) = lbest(cur_gene, :, min_index);
                regulator_combination(cur_gene,:) = pop(min_index,:);
            end % If change in best fitness occurs
         end % End of Iteration
         all_global_best_fitness(cur,cur_gene) = best_fitness;
         
         for i = 1:reg %% Creating regulator matrix
            index = regulator_combination(cur_gene,i);
            if(index > 0)
                result_matrix(cur_gene,index,cur) = gbest(cur_gene,i);
            end
         end % End of regulator matrix
  
    end % End of gene

end % End of repetition


%% Information Login
information_file_name = 'information.txt';
result_folder =  fullfile(folder,test_number);
diary(fullfile(result_folder,information_file_name));  % Start saving output to a file
diary on;
disp([test_number,':']);
disp(['Number of gene: ', num2str(n)]);
disp(['Population size : ',num2str(pop_size)]);
disp(['Dimension of population: ',num2str(dim)]);
test_describtion = "1. Removes the condition in half system to gene value less than or equal to  0" + ...
                    "2. Removes l1 normalization in fitness check" + ...
                    "3. Removes normalization in noise" + ...
                    "4. New time point expression prediction equation.";
disp('Test describtion :')
disp(test_describtion);
disp('----------------------------------');
diary off;

%% Result Saving and performance evaluation
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


for threshold = [0.8, 0.9, 1]
    output_file = ['output_thr_', num2str(threshold), '.txt'];
    diary(fullfile(main_folder,output_file));  % Start saving output to a file
    diary on;
    final_Matrix_2 = final_Matrix(:,:,1);
    final_Matrix_3 = final_Matrix_2 >= threshold;
    %disp(final_Matrix_3);
    TP = sum(sum((final_Matrix_3 == 1) & (actual_network == 1)));
    FP = sum(sum((final_Matrix_3 == 1) & (actual_network == 0)));
    FN = sum(sum((final_Matrix_3 == 0) & (actual_network == 1)));
    TN = sum(sum((final_Matrix_3 == 0) & (actual_network == 0)));
    
    accuracy = (TP + TN) ./ (TP + FP + FN + TN);
    precision = TP ./ (TP + FP);
    recall = TP ./ (TP + FN);
    f_score = 2 .* (precision .* recall) ./ (precision + recall);
    disp(['Confusion Matrix:[', num2str(threshold),']']);
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
    filename_obj = ['final_Matrix_',num2str(threshold),'.mat'];
    save(fullfile(main_folder, filename_obj), 'final_Matrix_3');
    
end
for run = 1:10
    sum_se = 0;
    for gn = 1:n
        sum_se = sum_se+all_global_best_fitness(run,gn);
    end
    MSE = sum_se/n;
    disp(['RUN : ', num2str(run),'| MSE : ',num2str(MSE)]);
        
end
