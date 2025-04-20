mainfolder = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\MATLAB\Half_System_PSO\result\';
testname = 'Test4\';
Geneset = {'GeneSet1\', 'GeneSet2\', 'GeneSet3\', 'GeneSet4\'};
n=8;
outputfolder = fullfile(mainfolder,testname);
for threshold = [0.8,0.9,1]
result_matrix = zeros(n,n,1);
final_matrix = zeros(n,n);
    for i = 1:length(Geneset)
        folderpath = fullfile(mainfolder, testname, Geneset{i});
        disp(['Reading folder: ', folderpath]);
        pattern = ['final_Matrix_',num2str(threshold),'.mat'];
        files = dir(fullfile(folderpath, pattern));
        for j = 1:length(files)
            filepath = fullfile(folderpath, files(j).name);
            loaded = load(filepath);
            fields = fieldnames(loaded);
            result_matrix = loaded.(fields{1});  % assumes one variable in the .mat file
            final_matrix = final_matrix + result_matrix;
        end
    end
    disp(final_matrix);
    pausibility_matrix = final_matrix/4;
    disp('pausibility_matrix :');
    disp(pausibility_matrix);
    filename_obj = ['final_Matrix_',num2str(threshold),'.mat'];
    save(fullfile(outputfolder, filename_obj), 'pausibility_matrix');
    
    % Actual network
    actual_network = [  
        0 1 0 0 0 0 0 0
        0 1 0 1 0 0 0 0
        0 1 0 0 0 0 0 0
        0 1 0 0 0 0 0 0
        0 1 0 0 0 0 0 0
        0 1 0 0 0 0 0 0
        0 1 0 0 0 0 0 0
        0 1 0 0 0 0 0 0
    ];
    
    % Threshold analysis
    for thr = [0.5, 0.75, 1]
        output_file = ['output_',num2str(threshold),'__', num2str(thr), '.txt'];
        diary(fullfile(outputfolder,output_file));  % Start saving output to a file
        diary on;
        final_Matrix_3 = pausibility_matrix >= thr;
        
        TP = sum(sum((final_Matrix_3 == 1) & (actual_network == 1)));
        FP = sum(sum((final_Matrix_3 == 1) & (actual_network == 0)));
        FN = sum(sum((final_Matrix_3 == 0) & (actual_network == 1)));
        TN = sum(sum((final_Matrix_3 == 0) & (actual_network == 0)));
    
        accuracy = (TP + TN) / (TP + FP + FN + TN);
        precision = TP / (TP + FP);
        recall = TP / (TP + FN);
        f_score = 2 * (precision * recall) / (precision + recall);
    
        disp(['Threshold: ', num2str(thr)]);
        disp(['TP: ', num2str(TP), ', FP: ', num2str(FP), ', FN: ', num2str(FN), ', TN: ', num2str(TN)]);
        disp(['Accuracy: ', num2str(accuracy)]);
        disp(['Precision: ', num2str(precision)]);
        disp(['Recall: ', num2str(recall)]);
        disp(['F-Score: ', num2str(f_score)]);
        disp('-----------------------------');
        diary off; 
    end
end




