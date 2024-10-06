import numpy as np
import random
import math
import pandas as pd

# Load data
data = pd.read_csv('Exp_1.csv', sep=',')

def fitness_function(T, array1, array2):
    return np.mean((array1 - array2) ** 2)

def sigmoid(x):
    return 1 / (1 + np.exp(-x))

def RNN(data, gene_index, position):
    T = len(data)
    gene_predicted = []
    delta_t = 6
    tau = position[9]
    gene_predicted.append(data.iloc[0].values[gene_index+1])
    for t in range(1, len(data)):
        gene_tp = data.iloc[t].values
        gene_tp_1 = gene_tp[1:]
        weight_mul = position[:8] * gene_tp_1
        s = sigmoid(sum(weight_mul) + position[8])
        Rnn = (delta_t/tau) * s + (1-(delta_t/tau)) * gene_tp_1[gene_index]
        gene_predicted.append(Rnn)
    gene_actual_data = data.iloc[:, gene_index+1]
    gene_actual = gene_actual_data.to_numpy()
    fitness = fitness_function(T, gene_actual, np.array(gene_predicted))
    return fitness

def levy_flight():
    beta = 1.5
    sigma = (math.gamma(1 + beta) * math.sin(math.pi * beta / 2) / 
             (math.gamma((1 + beta) / 2) * beta * 2**((beta - 1) / 2)))**(1 / beta)
    u = np.random.randn() * sigma
    v = np.random.randn()
    step = u / abs(v)**(1 / beta)
    return step

def MPA(num_gene, num_particle, max_iteration, base_seed):
    gene_best_positions = []
    
    for gene in range(num_gene):
        np.random.seed(base_seed + gene)
        random.seed(base_seed + gene)
        
        dim = num_gene + 2  # 8 genes + bias + tau
        lb, ub = -1, 1
        
        # Initialize prey population
        Prey = np.random.uniform(lb, ub, (num_particle, dim))
        
        # Initialize Elite matrix
        Elite = np.zeros((num_particle, dim))
        
        # Fitness calculation
        fitness = np.array([RNN(data, gene, Prey[i]) for i in range(num_particle)])
        
        I = np.argmin(fitness)
        Elite[0] = Prey[I]
        
        Iter = 0
        FADs = 0.2
        P = 0.5
        
        while Iter < max_iteration:
            # Update FADs
            if random.random() < FADs:
                U = np.random.rand(num_particle, dim) < FADs
                Prey = Prey + U * (np.random.randn(num_particle, dim) * (Elite - Prey))
            
            # Update A
            A = 2 * (1 - Iter / max_iteration) * (2 * Iter / max_iteration - 1)
            
            for i in range(num_particle):
                if Iter < max_iteration / 3:  # First phase
                    stepsize = np.random.randn(dim) * (Elite[i] - np.random.randn(dim) * Prey[i])
                    Prey[i] = Prey[i] + P * stepsize
                elif Iter < 2 * max_iteration / 3:  # Second phase
                    if i < num_particle / 2:
                        stepsize = levy_flight() * (Elite[i] - Prey[i])
                        Prey[i] = Elite[i] + P * A * stepsize
                    else:
                        stepsize = levy_flight() * (Elite[i] - Prey[i])
                        Prey[i] = Prey[i] + P * A * stepsize
                else:  # Third phase
                    stepsize = levy_flight() * (Elite[i] - Prey[i])
                    Prey[i] = Elite[i] + P * A * stepsize
            
            # Apply bounds
            Prey = np.clip(Prey, lb, ub)
            
            # Update fitness
            for i in range(num_particle):
                new_fitness = RNN(data, gene, Prey[i])
                if new_fitness < fitness[i]:
                    fitness[i] = new_fitness
                    if new_fitness < fitness[I]:
                        I = i
                        Elite[0] = Prey[i]
            
            Iter += 1
        
        gene_best_positions.append(Elite[0])
        print(f"Completed optimization for gene {gene+1}/{num_gene}")
    
    return np.array(gene_best_positions)

if __name__ == '__main__':
    num_gene = 8
    num_regulator = 4
    num_particle = math.comb(num_gene, num_regulator)
    max_iteration = 1000
    
    for cur in range(5):
        base_seed = 42 + (cur * 100)  # Ensures unique seeds across runs
        result_matrix = MPA(num_gene, num_particle, max_iteration, base_seed)
        csv_filename = f"result_matrix_MPA_{max_iteration}_{cur}_baseseed{base_seed}.csv"
        pd.DataFrame(result_matrix).to_csv(csv_filename, index=False)
        
        print(f"Completed iteration {cur+1}/5 with base seed {base_seed}")