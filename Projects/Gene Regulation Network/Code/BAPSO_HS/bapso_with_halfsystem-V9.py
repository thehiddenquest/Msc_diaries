import numpy as np
from pandas import read_csv,DataFrame
import time
import pdb
import matplotlib.pyplot as plt
from math import comb
from itertools import combinations
import os
import random

start_time = time.time()  # Record the start time
dataset_name = "Exp_2.csv"
#Insert files
data = read_csv(f'Dataset/{dataset_name}')
dataset = data.to_numpy()[:, 1:]

# Saving result

# Get the full path of the currently running script
script_path = __file__
# Extract the script's name (filename only, no directory)
script_name = os.path.basename(script_path)


# Initialization
iteration = 5000
num_of_gene = 8
num_of_regulator = 4
num_of_population = comb(num_of_gene,num_of_regulator)
repetition = 10
best_fitness_per_gene = np.zeros((num_of_gene,dataset.shape[0],1))
fitness_per_gene = np.zeros((num_of_gene,iteration))



# Create a new directory with the script's name
output_folder_dataset = f"Output/{dataset_name}"
if not os.path.exists(output_folder_dataset):
        os.makedirs(output_folder_dataset)
output_folder = output_folder_dataset+"/"+script_name+"_"+str(iteration)
os.makedirs(output_folder, exist_ok=True)  # Creates the folder if it doesn't exist


# Combination of regulator
genes = list(range(num_of_gene))
combination_of_regulator = combinations(genes, num_of_regulator)
regulator_combinations = list(combination_of_regulator)



for rep in range(repetition):
                start_rep_time = time.time()
                result_matrix = np.zeros((num_of_gene,num_of_gene))                                             # For 10 iteration  
                best_combination = np.zeros((num_of_gene,num_of_regulator), dtype=int)
                for gene in range(num_of_gene):
                                not_seeded_state = random.getstate()
                                random.seed(0+num_of_gene)                                                  # For 8 gene
                                global_best_position = np.zeros((num_of_population,num_of_gene-1))
                                position = np.zeros((num_of_population,num_of_regulator+3))     
                        # Initialization of particle properties
                                
                                # Initialization of fij, mui, delta_i, epsilon
                                
                                # Using random.uniform [-1,1] and [0,1]
                                fij = np.array([[random.uniform(-1, 1) for _ in range(num_of_regulator)] for _ in range(num_of_population)])  # Dimension: 70x4
                                mui = np.array([[random.uniform(0, 1)] for _ in range(num_of_population)])                                   # Dimension: 70x1
                                delta_i = np.array([[random.uniform(0, 1)] for _ in range(num_of_population)])                               # Dimension: 70x1
                                epsilon = np.array([[random.uniform(0, 1)] for _ in range(num_of_population)])                               # Dimension: 70x1
                                
                                # Concatenating into position
                                position = np.concatenate([fij, mui, delta_i, epsilon], axis=1)                # Dimension: 70*7
                                velocity = np.zeros((num_of_population,num_of_gene-1))                         # Dimension: 70*7
                                best_fitness = np.inf
                                
                        # Half System
                                
                                # Initialization of properties
                                delta_t = 6
                                
                                # Insert first value form the data set
                                gene_predicted = np.zeros((dataset.shape[0],num_of_population,1))
                                gene_predicted[0,:,:] = np.tile(dataset[0,gene],(num_of_population,1))
                                
                                for tp in range(1,dataset.shape[0]):                                            # For 49 time points
                                                regulator_dataset = dataset[tp, regulator_combinations]         # Shape: (70, 4)
                                                # Replace 0s with 1s in result_matrix
                                                regulator_dataset = np.where(regulator_dataset != 0, regulator_dataset, 1)
                                                expression = np.power(regulator_dataset,position[:,:4])
                                                row_prod = np.expand_dims(np.prod(expression, axis=1), axis=1)
                                                d_i = np.tile(dataset[tp-1,gene],(num_of_population,1)) - gene_predicted[tp-1,:,:]
                                                hs_expression = delta_t *( np.expand_dims(position[:, 5], axis=1) * row_prod)+(1-delta_t*np.expand_dims(position[:, 6], axis=1))*np.tile(dataset[tp-1,gene],(num_of_population,1))+ np.expand_dims(position[:, 4], axis=1)*d_i
                                                gene_predicted[tp,:,:]=hs_expression
                                tiled_data = np.broadcast_to(dataset[:, gene].reshape(-1, 1, 1), (dataset.shape[0], num_of_population, 1))
                                tiled_data_shaped = tiled_data[1:,:,:]
                                gene_predicted_shaped = gene_predicted[1:,:,:]
                                # Calculate the squared difference
                                squared_diff = (tiled_data_shaped - gene_predicted_shaped) ** 2
                                
                                # Compute the mean squared error (MSE)
                                fitness = np.mean(squared_diff,axis=0)
                                
                        # Initializing local best positon , fitness and global best position and fitness
                        
                                local_fitness = fitness.copy()
                                local_best_position = position.copy()
                                best_fitness = np.min(fitness)
                                global_best_position = local_best_position[np.argmin(fitness)].copy()
                                best_combination[gene,:] = np.array(regulator_combinations[np.argmin(fitness)])
                                #print(f"Gene {gene} Initial global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")

                        
                        # Updating global best fitness and position 

                                c1 = c2 = 2                                                                     # Acceleration factor
                                for itr in range(iteration):
                                                # Update the global best position and fitness
                                                
                                                # w = np.random.uniform(0,1)
                                                # r1 = np.random.uniform(0,1,(num_of_population,7))
                                                # r2 = np.random.uniform(0,1,(num_of_population,7))
                                                random.setstate(not_seeded_state)
                                                w = random.uniform(0,1)  # Single random number between 0 and 1

                                                # Generate 2D lists
                                                r1_list = [[random.uniform(0,1) for _ in range(7)] for _ in range(num_of_population)]  # 2D list of random numbers
                                                r2_list = [[random.uniform(0,1) for _ in range(7)] for _ in range(num_of_population)]  # 2D list of random numbers

                                                # Convert to NumPy arrays
                                                r1 = np.array(r1_list)
                                                r2 = np.array(r2_list)
                                                global_best_position_matrix = np.broadcast_to(global_best_position, (num_of_population, 7))
                                                
                                                # Update the velocity of each particle
                                                
                                                velocity = w * velocity + c1 * r1 * (local_best_position - position) + c2 * r2 * (global_best_position_matrix - position)
                                                                                                
                                                # Update the position of each particle
                                                
                                                
                                                
                                                # # Boolean mask identifying invalid positions
                                                # invalid_velocity_mask = (np.any(np.abs(velocity)>= 1, axis = 1) )
                                                # velocity[invalid_velocity_mask,:] = np.zeros((invalid_velocity_mask.sum(),num_of_gene-1))
                                                
                                             # Define the range constraints
                                                lower_bound_first_four = -1  # For the first four dimensions: range [-1, 1]
                                                upper_bound_first_four = 1

                                                lower_bound_rest = 0  # For dimensions 4, 5, 6: range [0, 1]
                                                upper_bound_rest = 1
                                                
                                                for i in range(velocity.shape[0]):
                                                                for j in range(velocity.shape[1]):
                                                                        if not (lower_bound_first_four <= velocity[i,j] <=upper_bound_first_four):
                                                                                        velocity[i,j] = random.uniform(lower_bound_first_four, upper_bound_first_four)

                                                position = position + velocity
                                                # Iterate over the matrix and check values for out-of-range entries
                                                for i in range(position.shape[0]):  # Loop through each row (particle)
                                                                for j in range(position.shape[1]):  # Loop through each dimension (column)
                                                                        if j < 4:  # First four dimensions: must be in range [-1, 1]
                                                                                        if not (lower_bound_first_four <= position[i, j] <= upper_bound_first_four):
                                                                                                        position[i, j] = random.uniform(lower_bound_first_four, upper_bound_first_four)
                                                                        else:  # Dimensions 4, 5, 6: must be in range [0, 1]
                                                                                        if not (lower_bound_rest <= position[i, j] <= upper_bound_rest):
                                                                                                        position[i, j] = random.uniform(lower_bound_rest, upper_bound_rest)
                                        

                                        # Calculating fitness
                                        
                                                gene_predicted = np.zeros((dataset.shape[0],num_of_population,1))
                                                gene_predicted[0,:,:] = np.tile(dataset[0,gene],(num_of_population,1))
                                                for tp in range(1,dataset.shape[0]):
                                                                regulator_dataset = dataset[tp, regulator_combinations]  # Shape: (70, 4)

                                                                regulator_dataset = np.where(regulator_dataset != 0, regulator_dataset, 1)

                                                                expression = np.power(regulator_dataset,position[:,:4])

                                                                row_prod = np.expand_dims(np.prod(expression, axis=1), axis=1)
                                                                d_i = np.tile(dataset[tp-1,gene],(num_of_population,1)) - gene_predicted[tp-1,:,:]

                                                                hs_expression = delta_t *( np.expand_dims(position[:, 5], axis=1) * row_prod)+(1-delta_t*np.expand_dims(position[:, 6], axis=1))*np.tile(dataset[tp-1,gene],(70,1))+np.expand_dims(position[:, 4], axis=1)*d_i

                                                                gene_predicted[tp,:,:]=hs_expression
                                                # Tile dataset[:, gene] to match the shape of gene_predicted (49, 70, 1)
                                                tiled_data = np.broadcast_to(dataset[:, gene].reshape(-1, 1, 1), (dataset.shape[0], num_of_population, 1))
                                                tiled_data_shaped = tiled_data[1:,:,:]
                                                gene_predicted_shaped = gene_predicted[1:,:,:]
                                                # Calculate the squared difference
                                                squared_diff = (tiled_data_shaped - gene_predicted_shaped) ** 2

                                                # Compute the mean squared error (MSE)
                                                fitness = np.mean(squared_diff,axis=0)
                                        
                                        # Updating best fitness and positions
                                                better_mask = fitness < local_fitness
                                                local_fitness = np.where(better_mask,fitness,local_fitness)

                                                for i in range(num_of_population):
                                                                if better_mask[i]:  # If fitness is better than local_fitness for individual i
                                                                                local_best_position[i,:] = position[i,:]  # Update the corresponding row
                                                min_fitness = np.min(fitness)
                                                if best_fitness > min_fitness:
                                                        best_fitness = min_fitness
                                                        global_best_position = local_best_position[np.argmin(fitness)].copy()
                                                        best_combination[gene,:] = np.array(regulator_combinations[np.argmin(fitness)]).copy()
                                                #        print(f"Gene {gene} Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)} where combination is {best_combination[gene,:]}")
                                                fitness_per_gene[gene,itr] = best_fitness.copy()
                                result_matrix[gene,best_combination[gene,:]] = global_best_position[:4]
                                print(f"------------repetition:: {rep} for gene:: {gene} is done-------------- ")                
                #print(result_matrix)
                csv_filename_resultant = output_folder+"/result_matrix"+"_"+str(iteration)+"_"+str(rep)+".csv"
                DataFrame(result_matrix).to_csv(csv_filename_resultant, index=False)
                end_rep_time = time.time()
                execution_rep_time = end_rep_time - start_rep_time  # Calculate the difference
                print(f"Execution time for repetition {rep} is : {execution_rep_time:.4f} seconds")
                 
end_time = time.time()  # Record the end time
execution_time = end_time - start_time  # Calculate the difference
print(f"Execution time: {execution_time:.4f} seconds")
