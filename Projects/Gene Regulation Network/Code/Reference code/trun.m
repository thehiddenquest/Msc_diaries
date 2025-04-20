% Parameters
folderPath = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\Reference code\Grn Result\'; % Change to your folder path
matFiles = dir(fullfile(folderPath, '*.mat'));
numFiles = min(10, length(matFiles));

% Load size from first gbest_pos to initialize
sample = load(fullfile(folderPath, matFiles(1).name));
n = size(sample.gbest_pos, 1);

% Initialization
final_Matrix = zeros(n, n);
final_Matrix_1 = zeros(n, n, numFiles);


% Load and build matrix
for run = 1:numFiles
    data = load(fullfile(folderPath, matFiles(run).name));
    if isfield(data, 'gbest_pos')
        result_matrix = data.gbest_pos;
        final_Matrix_1(:, :, run) = abs(result_matrix) > 0;
        final_Matrix = final_Matrix + final_Matrix_1(:, :, run);

    else
        warning('gbest_pos not found in %s', matFiles(run).name);
    end
end

% Average
final_Matrix = final_Matrix / numFiles;

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
for thr = [0.8, 0.9, 1]
    final_Matrix_3 = final_Matrix >= thr;

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
end
