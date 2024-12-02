import numpy as np
import csv
import os
from sklearn.metrics import confusion_matrix
import seaborn as sns
import matplotlib.pyplot as plt

def read_csv_to_matrix(filename):
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        next(reader)  
        data = list(reader) 
        return np.array([list(map(float, row)) for row in data])

def process_csv_files(file_pattern, num_files):
    matrices = []
    for i in range(num_files):
        filename = file_pattern.format(i)
        if os.path.exists(filename):
            matrix = read_csv_to_matrix(filename)
            matrices.append(np.abs(matrix))
        else:
            print(f"Warning: File {filename} not found. Skipping.")
    
    if not matrices:
        raise ValueError("No valid matrices found.")
    matrices_1 =[]
    for matrix in matrices:
        matrix_1 = apply_threshold(matrix,0.8)
        matrices_1.append(matrix_1)

#    print(matrices_1)
    avg_matrix_1 = np.mean(matrices_1, axis=0)
    return avg_matrix_1
#    print(avg_matrix_1)
#    print("final matrix :", apply_threshold(avg_matrix_1,0.8))
    # Calculate the average matrix
    # avg_matrix = np.mean(matrices, axis=0)
    # return avg_matrix

def apply_threshold(matrix, threshold):
    return np.where(matrix >= threshold, 1, 0)

def calculate_confusion_matrix(predictions, ground_truth):
    # Ensure both matrices have the same shape
    if predictions.shape != ground_truth.shape:
        raise ValueError(f"Shape mismatch: predictions shape {predictions.shape}, ground truth shape {ground_truth.shape}")
    
    # Flatten the matrices
    return confusion_matrix(ground_truth.flatten(), predictions.flatten())

def plot_confusion_matrix(cm, filename):
    plt.figure(figsize=(10, 8))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues')
    plt.title('Confusion Matrix')
    plt.ylabel('True Label')
    plt.xlabel('Predicted Label')
    plt.savefig(filename)
    plt.close()

def save_metrics_to_csv(metrics, filename='metrics.csv'):
    with open(filename, 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['Metric', 'Value'])
        for key, value in metrics.items():
            writer.writerow([key, value])

# Main execution
folder_name = 'Output/bapso_with_halfsystem-V4.py_5500/'
num_files = 10
file_pattern = folder_name+"resultant_matrix_5500_{}.csv"
eleventh_file = "Dataset/actual_data.csv"
threshold = 0.8

try:
    # Process the first 10 CSV files
    avg_matrix = process_csv_files(file_pattern, num_files)
    print(avg_matrix)
    
    # Apply threshold to the averaged matrix
    thresholded_matrix = apply_threshold(avg_matrix, threshold)
    print(thresholded_matrix)
    
    # Read the 11th CSV file
    ground_truth = read_csv_to_matrix(eleventh_file)
    
    # Apply the same threshold to the ground truth
    thresholded_ground_truth = apply_threshold(ground_truth, threshold)
    
    # Check if the matrices have the same shape
    if thresholded_matrix.shape != thresholded_ground_truth.shape:
        print(f"Warning: Shape mismatch. Averaged matrix shape: {thresholded_matrix.shape}, Ground truth matrix shape: {thresholded_ground_truth.shape}")
    
    # Calculate confusion matrix only if shapes match
    if thresholded_matrix.shape == thresholded_ground_truth.shape:
        cm = calculate_confusion_matrix(thresholded_matrix, thresholded_ground_truth)
        
        # Extract TP, TN, FP, FN from confusion matrix
        tn, fp, fn, tp = cm.ravel()
        
        # Print confusion matrix results
        print(f"True Positives (TP): {tp}")
        print(f"True Negatives (TN): {tn}")
        print(f"False Positives (FP): {fp}")
        print(f"False Negatives (FN): {fn}")
        
        # Calculate performance metrics
        Sn = tp / (tp + fn)  # Sensitivity
        Sp = tn / (tn + fp)  # Specificity
        PPV = tp / (tp + fp)  # Positive Predictive Value
        ACC = (tp + tn) / (tp + tn + fp + fn)  # Accuracy
        F = 2 * tp / (2*tp+ fp + fn)  # F Score
        
        # Store all metrics in a dictionary
        metrics = {
            'Sensitivity (Sn)': Sn,
            'Specificity (Sp)': Sp,
            'Positive Predictive Value (PPV)': PPV,
            'Accuracy (ACC)': ACC,
            'F Score': F
        }
        
        # Print performance metrics
        for key, value in metrics.items():
            print(f"{key}: {value:.4f}")
        
        # Save performance metrics to a CSV file
        csv_filename = folder_name+'metrics_threshold_'+str(threshold)+".csv"
        save_metrics_to_csv(metrics, filename=csv_filename)
        print(f"Metrics have been saved to 'metrics_threshold_{threshold}.csv'")
        
        # Plot and save confusion matrix
        cm_filename = folder_name+'confusion_matrix_threshold_'+str(threshold)+'.png'
        plot_confusion_matrix(cm,cm_filename)
        print(f"Confusion matrix has been saved as 'confusion_matrix_threshold_{threshold}.png'")
    else:
        print("Error: Matrix shapes do not match. Cannot calculate confusion matrix.")
    
except Exception as e:
    print(f"An error occurred: {e}")