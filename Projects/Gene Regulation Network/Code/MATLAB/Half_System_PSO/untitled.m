% Define the folder path (make sure this is set to the actual folder location)
folderPath = 'F:\Masters in Computer and Information Science\CSMC\Projects\Gene Regulation Network\Code\Reference code\Grn Result\'; % Change to your folder path
matFiles = dir(fullfile(folderPath, '*.mat'));
numFiles = min(10, length(matFiles));

% Load the result_matrix from the saved .mat file
thrashold = 0.8;  % Example initial value to load the correct file
load(fullfile(folder, filename_obj), matl);

% Assuming result_matrix is already in your workspace or loaded separately
[n, ~, ~] = size(final_Matrix_3);  % Get matrix dimensions

% Initialize final_Matrix
final_Matrix = zeros(n, n, 1);

% Combine binary matrices for each result set
for result_set = 1:4
    final_Matrix_1(:,:,result_set) = (abs(result_matrix(:,:,result_set)) > 0);
    final_Matrix(:,:,1) = final_Matrix(:,:,1) + final_Matrix_1(:,:,result_set);
end

% Define the actual network
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

% Normalize the matrix
final_Matrix(:,:,1) = final_Matrix(:,:,1) / 10;

% Evaluation for multiple thresholds
    output_file = ['output_thr_', num2str(thrashold), '.txt'];
    diary(fullfile(folder, output_file));  % Start saving output to a file
    diary on;

    final_Matrix_2 = final_Matrix(:,:,1);
    final_Matrix_3 = final_Matrix_2 >= thrashold;

    % Compute metrics
    TP = sum(sum((final_Matrix_3 == 1) & (actual_network == 1)));
    FP = sum(sum((final_Matrix_3 == 1) & (actual_network == 0)));
    FN = sum(sum((final_Matrix_3 == 0) & (actual_network == 1)));
    TN = sum(sum((final_Matrix_3 == 0) & (actual_network == 0)));

    accuracy = (TP + TN) / (TP + FP + FN + TN);
    precision = TP / (TP + FP);
    recall = TP / (TP + FN);
    f_score = 2 * (precision * recall) / (precision + recall);

    % Display results
    disp(['Confusion Matrix: [', num2str(thrashold), ']']);
    disp(['True Positive (TP): ', num2str(TP)]);
    disp(['False Positive (FP): ', num2str(FP)]);
    disp(['False Negative (FN): ', num2str(FN)]);
    disp(['True Negative (TN): ', num2str(TN)]);
    disp(['Accuracy: ', num2str(accuracy)]);
    disp(['Precision: ', num2str(precision)]);
    disp(['Recall: ', num2str(recall)]);
    disp(['F-Score: ', num2str(f_score)]);
    disp('----------------------------------');

    % Save final thresholded matrix
    filename_obj = ['final_Matrix_thr_', num2str(thrashold), '.mat'];
    save(fullfile(folder, filename_obj), 'final_Matrix_3');

    diary off;