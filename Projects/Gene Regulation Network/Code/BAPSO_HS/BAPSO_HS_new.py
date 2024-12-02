from itertools import combinations
import math
import random
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import time



def BAPSO(data,num_gene,num_regulator,population_size,combination,Iteration):
    total_timepoints = data.shape[0]
    delta_t = 6
    positions = np.zeros((population_size, num_regulator+3))
    velocities = np.zeros((population_size, num_regulator+3))
    
    
    for gene in range(num_gene):
        positions[0:,0:4] = np.random.uniform(-1,1,(population_size,4))
        positions[0:,4:] = np.random.uniform(0,1,(population_size,3))
        # print(f"Positions : {positions} Length : {len(positions)}")
        # print(f"velocities : {velocities} Length : {len(positions)}")
        
        fitness = np.zeros((population_size,1))
        best_fitness = None
        global_best_position = None
        local_best_position = np.zeros((population_size, num_regulator+3))
        for pop in range(population_size):
            predicted = []
            i = int(1)
            for tp in range(total_timepoints):
                if tp == 0:
                    predicted.append(data[tp,gene])
                else:
                    expression = data[tp,combination[pop]]
                    expression = [expression[i] if expression[i] != 0 else 1 for i in range(len(expression))]
                    expression = np.power(expression,positions[pop,:4])
                    print(f"{i} expression : {expression}")
                    product = np.prod(expression)
                    di = np.abs(data[tp-1,gene] - predicted[-1])
                    print(f"product : {product}")
                    i+=1
                    predicted_expression = (delta_t*(positions[pop,4]*product))+(1 - delta_t*positions[pop,5])*predicted[-1] + positions[pop,6]*di
                    predicted.append(predicted_expression)
            print(f"predicted expression : {predicted} Length : {len(predicted)}")
            fitness[pop] = np.sum((predicted - data[:,gene])**2)/len(data[:,gene])
            local_best_position[pop] = positions[pop]
        best_fitness = np.min(fitness)
        global_best_position = local_best_position[np.argmin(fitness)]
        print(f"Fitnesses : {best_fitness}")
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        
        w = np.random.uniform(0,1)
        r1 = np.random.random()
        r2 = np.random.random()
        c1 = c2 = 2
        
        for iter in range(Iteration):
            print(f"For Epoch : {iter}")
            global_best_position_matrix = np.tile(global_best_position,(70,1)) 
            print(f'Global_best_position_matrix{global_best_position_matrix} Length : {global_best_position_matrix.shape[0]}')
            velocities = w * velocities + c1*r1*(local_best_position -positions) + c2*r2*(global_best_position_matrix - positions)
            positions = positions + velocities
            for pop in range(population_size):
                predicted = []
                i = int(1)
                for tp in range(total_timepoints):
                    if tp == 0:
                        predicted.append(data[tp,gene])
                    else:
                        expression = data[tp,combination[pop]]
                        expression = [expression[i] if expression[i] != 0 else 1 for i in range(len(expression))]
                        expression = np.power(expression,positions[pop,:4])
                        print(f"{i} expression : {expression}")
                        product = np.prod(expression)
                        di = np.abs(data[tp-1,gene] - predicted[-1])
                        print(f"product : {product}")
                        i+=1
                        predicted_expression = (delta_t*(positions[pop,4]*product))+(1 - delta_t*positions[pop,5])*predicted[-1] + positions[pop,6]*di
                        predicted.append(predicted_expression)
                print(f"predicted expression : {predicted} Length : {len(predicted)}")
                print(f'Actual expression : {data[:,gene]} Length : {len(data[:,gene])}')
                if fitness[pop] > np.sum((predicted - data[:,gene])**2)/len(data[:,gene]):
                    fitness[pop] = np.sum((predicted - data[:,gene])**2)/len(data[:,gene])
                    local_best_position[pop] = positions[pop]
                    if best_fitness > fitness[pop]:
                        best_fitness = np.min(fitness)
                        global_best_position = local_best_position[np.argmin(fitness)]
                        print(f"Updated Fitnesses : {best_fitness}")
                        print(f"Updated Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        print(f"Fitnesses : {fitness}")
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")

if __name__ == '__main__':
    #start of execution
    start_time = time.time()
    
    data = pd.read_csv('Dataset\Exp_1.csv', sep=',')
    data = np.array(data)
    num_gene = data.shape[1] - 1
    genes = list(range(num_gene))
    num_regulator = 4
    combination = np.array(list(combinations(genes, num_regulator)))
    population_size = combination.shape[0]
    Iteration = 1000
    # print(f"population_size : {population_size}")
    # print(f"Number of gene : {num_gene}")
    print(f"Gene Expression : {data[:,1:]}")
    # print(f"Combination : {combination}")
    BAPSO(data[:,1:],num_gene,num_regulator,population_size,combination,Iteration)
    
    #calculating execution time
    print("--- %s seconds ---" % (time.time() - start_time))



