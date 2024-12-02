import numpy as np
from pandas import read_csv,DataFrame
import time
import pdb
import matplotlib.pyplot as plt
from math import comb
from itertools import combinations
import os
import random
from pprint import pprint

start_time = time.time()  # Record the start time

# Read csv file
data = read_csv('Dataset/Exp_1.csv')
dataset = data.to_numpy()[:, 1:]
print(dataset)

# Initialize of variables
iteration = 1000
num_of_gene = 8
num_of_regulator = 4
num_of_population = comb(num_of_gene,num_of_regulator)


# Combination of regulator
genes = list(range(num_of_gene))
combination_of_regulator = combinations(genes, num_of_regulator)
regulator_combinations = list(combination_of_regulator)
print(regulator_combinations)


# BAPSO:- 
for gene in range(num_of_gene):
         fij = np.random.uniform(-1,1,(num_of_population,num_of_regulator))             # Dimension: 70*4
         mui = np.random.uniform(0,1,(num_of_population,1))                             # Dimension: 70*4
         delta_i = np.random.uniform(0,1,(num_of_population,1))                         # Dimension: 70*4
         epsilon = np.random.uniform(0,1,(num_of_population,1))                         # Dimension: 70*4
                   
         position = np.concatenate([fij, mui, delta_i, epsilon], axis=1)                # Dimension: 70*7
         velocity = np.zeros((num_of_population,num_of_gene-1))                         # Dimension: 70*7
        
         print(position)
         print(velocity)
         #DataFrame(position).to_csv("initial_position.csv", index=False, header=False)
         
         # Half system :
         
         delta_t = 6
         gene_predicted = np.zeros((49,70,1))
         gene_predicted[0,:,:] = np.tile(dataset[0,gene],(70,1))
         print(gene_predicted)
         print(gene_predicted.shape)
         # Initialize the final matrix (49 rows, 70 combinations, 4 columns per combination)
         #result_matrix = np.zeros((49, len(regulator_combinations), num_of_regulator))

         #  # Fill the matrix
         #  for idx, comb in enumerate(regulator_combinations):
         #          result_matrix[:, idx, :] = dataset[:, comb]
         #  print(result_matrix)
         #pdb.set_trace() # to check both matrix use np.array_equal(resultmatrix[tp,:,:], resultmatrix1) inside tp loop
         # pprint(result_matrix.tolist())  # Convert to list for pprint
         for tp in range(1,dataset.shape[0]):
                regulator_dataset = dataset[tp, regulator_combinations]  # Shape: (70, 4)
                #print(result_matrix)
                # Replace 0s with 1s in result_matrix
                regulator_dataset = np.where(regulator_dataset != 0, regulator_dataset, 1)
                #DataFrame(regulator_dataset).to_csv(f"regulator_dataset_tp_{tp}.csv", index=False, header=False)
                #print(result_matrix.shape)
                expression = np.power(regulator_dataset,position[:,:4])
                #DataFrame(expression).to_csv(f"expression_tp_{tp}.csv", index=False, header=False)
                row_prod = np.expand_dims(np.prod(expression, axis=1), axis=1)
                #DataFrame(row_prod).to_csv(f"product_tp_{tp}.csv", index=False, header=False)
                print(row_prod.shape)
                d_i = np.tile(dataset[tp-1,gene],(70,1)) - gene_predicted[tp-1,:,:]
                print(d_i)
                print(d_i.shape)
                
                hs_expression = (delta_t * np.expand_dims(position[:, 5], axis=1) * row_prod)+(1-delta_t*np.expand_dims(position[:, 6], axis=1))*np.tile(dataset[tp-1,gene],(70,1))+np.expand_dims(position[:, 4], axis=1)*d_i
                print(position[:,5].shape)
                print(hs_expression)
                print(hs_expression.shape)
                gene_predicted[tp,:,:]=hs_expression
         # Tile dataset[:, gene] to match the shape of gene_predicted (49, 70, 1)
         tiled_data = np.tile(dataset[:, gene].reshape(-1, 1, 1), (1, 70, 1))

         # Calculate the squared difference
         squared_diff = (gene_predicted - tiled_data) ** 2

         # Compute the mean squared error (MSE)
         fitness = np.mean(squared_diff,axis=0)
         print(fitness)  # This will be a single scalar value
         print(fitness.shape)
         local_best_position = position
         best_fitness = np.min(fitness)
         pdb.set_trace()
         global_best_position = local_best_position[np.argmin(fitness)]
         print(global_best_position)
         print(global_best_position.shape)
         print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
         
         
         #Update the global best position and fitness
         w = np.random.uniform(0,1,(70,1))
         r1 = np.random.uniform(0,1,(70,1))
         r2 = np.random.uniform(0,1,(70,1))
         c1 = c2 = 2
         for itr in range(iteration):
                 global_best_position_matrix = np.broadcast_to(global_best_position, (70, 7))
                 # Update the velocity of each particle
                 velocity = w * velocity + c1 * r1 * (local_best_position - position) + c2 * r2 * (global_best_position_matrix - position)
                 # Update the position of each particle
                 position = position + velocity
                 print(velocity)
                 print(position)
                 print(velocity.shape)
                 print(position.shape)
end_time = time.time()  # Record the end time
execution_time = end_time - start_time  # Calculate the difference
print(f"Execution time: {execution_time:.4f} seconds")