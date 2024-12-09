import numpy as np
from pandas import read_csv,DataFrame
import time
import pdb
import matplotlib.pyplot as plt
from math import comb
from itertools import combinations
import os

start_time = time.time()  # Record the start time

# Read csv file
data = read_csv('Dataset/Exp_1.csv')
dataset = data.to_numpy()[:, 1:]
np.random.seed(42)
# Initialize of variables
iteration = 5000
num_of_gene = 8
num_of_regulator = 4
num_of_population = comb(num_of_gene,num_of_regulator)

best_fitness_per_gene = np.zeros((num_of_gene,dataset.shape[0],1))
fitness_per_gene = np.zeros((num_of_gene,iteration))
result_matrix = np.zeros((num_of_gene,num_of_gene))
best_combination = np.zeros((num_of_gene,num_of_regulator), dtype=int)



# Combination of regulator
genes = list(range(num_of_gene))
combination_of_regulator = combinations(genes, num_of_regulator)
regulator_combinations = list(combination_of_regulator)


# BAPSO:- 
for gene in range(num_of_gene):
         fij = np.random.uniform(-1,1,(num_of_population,num_of_regulator))             # Dimension: 70*4
         mui = np.random.uniform(0,1,(num_of_population,1))                             # Dimension: 70*4
         delta_i = np.random.uniform(0,1,(num_of_population,1))                         # Dimension: 70*4
         epsilon = np.random.uniform(0,1,(num_of_population,1))                         # Dimension: 70*4
                   
         position = np.concatenate([fij, mui, delta_i, epsilon], axis=1)                # Dimension: 70*7
         velocity = np.zeros((num_of_population,num_of_gene-1))                         # Dimension: 70*7
         best_fitness = np.inf
         
         # Half system :
         
         delta_t = 6
         gene_predicted = np.zeros((dataset.shape[0],num_of_population,1))
         gene_predicted[0,:,:] = np.tile(dataset[0,gene],(num_of_population,1))

         for tp in range(1,dataset.shape[0]):
                regulator_dataset = dataset[tp, regulator_combinations]  # Shape: (70, 4)
                # Replace 0s with 1s in result_matrix
                regulator_dataset = np.where(regulator_dataset != 0, regulator_dataset, 1)
                expression = np.power(regulator_dataset,position[:,:4])
                row_prod = np.expand_dims(np.prod(expression, axis=1), axis=1)
                d_i = np.tile(dataset[tp-1,gene],(70,1)) - gene_predicted[tp-1,:,:]
                hs_expression = (delta_t * np.expand_dims(position[:, 5], axis=1) * row_prod)+(1-delta_t*np.expand_dims(position[:, 6], axis=1))*np.tile(dataset[tp-1,gene],(70,1))+np.expand_dims(position[:, 4], axis=1)*d_i
                gene_predicted[tp,:,:]=hs_expression

         tiled_data = np.tile(dataset[:, gene].reshape(-1, 1, 1), (1, 70, 1))

         # Calculate the squared difference
         squared_diff = (gene_predicted - tiled_data) ** 2

         # Compute the mean squared error (MSE)
         fitness = np.mean(squared_diff,axis=0)
         local_fitness = fitness.copy()
         local_best_position = position.copy()
         best_fitness = np.min(fitness)
         global_best_position = local_best_position[np.argmin(fitness)].copy()
         best_combination[gene,:] = np.array(regulator_combinations[np.argmin(fitness)])
         print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
         
         # Accelaration factor
         c1 = c2 = 2
         for itr in range(iteration):
              #Update the global best position and fitness
               w = np.random.uniform(0,1)
               r1 = np.random.uniform(0,1,(70,7))
               r2 = np.random.uniform(0,1,(70,7))
               global_best_position_matrix = np.broadcast_to(global_best_position, (70, 7))
               # Update the velocity of each particle
               velocity = w * velocity + c1 * r1 * (local_best_position - position) + c2 * r2 * (global_best_position_matrix - position)
               # Update the position of each particle
               position = position + velocity
               # Boolean mask identifying invalid positions
               invalid_mask = (
                     np.any(np.abs(position[:, :4]) >= 1, axis=1) |  # First four dimensions condition
                     np.any(position[:, 4:] <= 0, axis=1)           # Last three dimensions condition
                  )
               position[invalid_mask, :4] = np.random.uniform(-1, 1, (invalid_mask.sum(), 4))
               position[ invalid_mask, 4:] = np.random.uniform(0, 1, (invalid_mask.sum(), 3))
                 
               # Recalculating fitness
               gene_predicted = np.zeros((49,70,1))
               gene_predicted[0,:,:] = np.tile(dataset[0,gene],(70,1))
               for tp in range(1,dataset.shape[0]):
                     regulator_dataset = dataset[tp, regulator_combinations]  # Shape: (70, 4)

                     regulator_dataset = np.where(regulator_dataset != 0, regulator_dataset, 1)
   
                     expression = np.power(regulator_dataset,position[:,:4])

                     row_prod = np.expand_dims(np.prod(expression, axis=1), axis=1)
                     d_i = np.tile(dataset[tp-1,gene],(70,1)) - gene_predicted[tp-1,:,:]
                
                     hs_expression = (delta_t * np.expand_dims(position[:, 5], axis=1) * row_prod)+(1-delta_t*np.expand_dims(position[:, 6], axis=1))*np.tile(dataset[tp-1,gene],(70,1))+np.expand_dims(position[:, 4], axis=1)*d_i

                     gene_predicted[tp,:,:]=hs_expression
              # Tile dataset[:, gene] to match the shape of gene_predicted (49, 70, 1)
               tiled_data = np.tile(dataset[:, gene].reshape(-1, 1, 1), (1, 70, 1))

              # Calculate the squared difference
               squared_diff = (gene_predicted - tiled_data) ** 2

                 # Compute the mean squared error (MSE)
               fitness = np.mean(squared_diff,axis=0)
                 
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
                        print(f"Gene {gene} Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)} where combination is {best_combination[gene,:]}")
               fitness_per_gene[gene,itr] = best_fitness.copy()
         
         result_matrix[gene,best_combination[gene,:]] = global_best_position[:4]           
print(best_combination)
print(type(best_combination))
print(fitness_per_gene)
print(np.min(fitness_per_gene,axis = 1))
print(fitness_per_gene.shape)
print(result_matrix)

                 
end_time = time.time()  # Record the end time
execution_time = end_time - start_time  # Calculate the difference
print(f"Execution time: {execution_time:.4f} seconds")