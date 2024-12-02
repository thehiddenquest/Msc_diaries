import math
import random
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from math import gamma

data = pd.read_csv('Exp_1.csv', sep=',')

class Particle:
    def __init__(self, current_position,particle_best_position,velocity, fitness):
        self.current_position = np.array(current_position)
        self.particle_best_position = np.array(particle_best_position)
        self.velocity = np.array(velocity)
        self.fitness = fitness

def initialize(x_min, x_max, dimension):
    current_position = np.random.uniform(x_min,x_max,dimension)
    return current_position

def levy(dimenssion):
    beta = 1.5
    sigma = (gamma(1 + beta) * np.sin(np.pi * beta / 2) / (gamma((1 + beta) / 2) * beta * 2 ** ((beta - 1) / 2))) ** (1 / beta)
    u = np.random.randn(dimenssion) * sigma
    v = np.random.randn(dimenssion)
    return u / np.abs(v) ** (1 / beta)

def fitness_function(T,array1,array2):
    # Compute the squared error for each element
    squared_error = np.mean((array1-array2) ** 2)
    return squared_error

def sigmoid(x):
        return 1 / (1 + np.exp(-x))

def  RNN(data,gene_index,current_position):
  T = len(data)
  gene_predicted = []
  delta_t = 6
  tau = current_position[9]
  gene_predicted.append(data.iloc[0].values[gene_index+1])
  for t in range(1, len(data)):
      gene_tp = data.iloc[t].values
      gene_tp_1 = gene_tp[1:]
      weight_mul =  current_position[:8] * gene_tp_1
      s = sigmoid(sum(weight_mul)+current_position[8])
      Rnn = (delta_t/tau) * s + (1-(delta_t/tau)) * gene_tp_1[gene_index]
      gene_predicted.append(Rnn)
  gene_actual_data = data.iloc[:, gene_index+1]
  gene_actual = gene_actual_data.to_numpy()
#  fitness = (1/T)  * sum((column_array - gene_predicted)**2)
  fitness = fitness_function(T,gene_actual,np.array(gene_predicted))
  return fitness

def plot_fitness(fitness_per_gene, max_iteration,cur):
    plt.figure(figsize=(12, 8))
    for gene, fitness_values in enumerate(fitness_per_gene):
        plt.plot(range(max_iteration), fitness_values, label=f'Gene {gene+1}')
    plt_filename = 'fitness_evolution_APSO_V@2'+'_'+str(max_iteration)+'_'+str(cur)+".png"
    plt.xlabel('Iteration')
    plt.ylabel('Fitness (MSE)')
    plt.title('Fitness Evolution per Gene')
    plt.legend()
    plt.yscale('log')  # Use log scale for y-axis to better visualize improvements
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(plt_filename)
    plt.close()

def AO(num_gene,population, dimension, max_iteration,x_min,x_max):
    gene_gbest_position =[]
    fitness_per_gene = [[] for _ in range(num_gene)]
    for gene in range(num_gene):
        #print("For Gene :", gene)
        #print("Method called")
        alpha = 0.1
        delta = 0.1
        best_particle_position = np.zeros(dimension)
        best_particle_fitness = float('inf')
        particles = []
        for _ in range(population):
            current_position = initialize(x_min, x_max, dimension)
            #print(current_position)
            velocity = np.random.randn(dimension)
            particles.append(Particle(current_position,current_position,velocity, float('inf')))
        for iter in range(1,max_iteration+1):
            #print("epoch : ", iter)
            #print("................")
            #PSO
            r1 = np.random.rand()
            r2 = np.random.rand()
            accel_factor = 2
            w_init = 0.9
            w_end = 0.4
            w = w_init - (w_init - w_end) * iter / max_iteration
            #PSO
            G1 = 2 * np.random.rand() - 1
            G2 = 2 * (1 - (iter / max_iteration))
            to = np.arange(dimension)
            u = 0.0265  #0.005650
            r0 = 10
            r = r0 + u * to
            omega = 0.005
            phi0 = 3 * np.pi / 2
            phi = -omega * to + phi0
            x = r * np.sin(phi)
            y = r * np.cos(phi)
            QF = iter ** ((2 * np.random.uniform(0, 1) - 1) / (1 - max_iteration) ** 2)
            for particle in particles:
                if np.any(np.abs(particle.current_position) >= x_max):
                    particle.current_position = initialize(x_min, x_max, dimension)
                fitness = RNN(data,gene,particle.current_position)
                particle.fitness = fitness
                #print(f"Prey Position: {particle.current_position}, Fitness: {particle.fitness}")
                if best_particle_fitness > fitness:
                    best_particle_fitness = fitness
                    best_particle_position = particle.current_position.copy()
                    print(f"**CHANGED** For gene : {gene} iter : {iter} | top_particle_position: {best_particle_position}, top_particle_fitness: {best_particle_fitness}")
            for particle in particles: 
                if iter <= (2/3)*max_iteration:
                    if np.random.random() <= 0.5:
                        #print(".....................")
                        #print("Expanded Exploration")
                        #print(".....................")
                        if np.random.random() <= 0.5:
                            all_positions = np.array([particle.particle_best_position for particle in particles])
                            mean_position = np.mean(all_positions, axis=0)
                            particle.current_position = np.array(best_particle_position) * (1 - iter / max_iteration) + (mean_position - best_particle_position) * np.random.rand()
                        else:
                            #print("PSO")
                            particle.velocity = w * particle.velocity +accel_factor * r2 * (best_particle_position - particle.current_position)+accel_factor * r1 * (particle.particle_best_position - particle.current_position)
                            particle.current_position = particle.current_position + particle.velocity
                        if np.any(np.abs(particle.current_position) >= x_max):
                            particle.current_position = initialize(x_min, x_max, dimension)
                        fitness = RNN(data,gene,particle.current_position)
                        #print(f"New Position: {particle.current_position}, Fitness: {fitness}")
                        if particle.fitness > fitness:
                            particle.particle_best_position = particle.current_position
                            particle.fitness = fitness
                            #print(f"**UPDATED**Particle best Position: {particle.particle_best_position}, Fitness: {particle.fitness}")
                            if best_particle_fitness > particle.fitness:
                                best_particle_fitness = particle.fitness
                                best_particle_position = particle.particle_best_position.copy()
                                print(f"**CHANGED** For gene : {gene} iter : {iter} | top_particle_position: {best_particle_position}, top_particle_fitness: {best_particle_fitness}")

                    else:
                        #print(".....................")
                        #print("Narrowed Exploration")
                        #print(".....................")
                        if np.random.random() <= 0.5:
                            particle.current_position = best_particle_position * levy(dimension) + random.choice(particles).current_position+ (y - x) * np.random.rand(dimension)
                        else:
                            #print("PSO")
                            particle.velocity = w * particle.velocity +accel_factor * r2 * (best_particle_position - particle.current_position)+accel_factor * r1 * (particle.particle_best_position - particle.current_position)
                            particle.current_position = particle.current_position + particle.velocity
                        if np.any(np.abs(particle.current_position) >= x_max):
                            particle.current_position = initialize(x_min, x_max, dimension)
                        fitness = RNN(data,gene,particle.current_position)
                        #print(f"New Position: {particle.current_position}, Fitness: {fitness}")
                        if particle.fitness > fitness:
                            particle.particle_best_position = particle.current_position
                            particle.fitness = fitness
                            #print(f"**UPDATED**Particle best Position: {particle.particle_best_position}, Fitness: {particle.fitness}")
                            if best_particle_fitness > particle.fitness:
                                best_particle_fitness = particle.fitness
                                best_particle_position = particle.particle_best_position.copy()
                                print(f"**CHANGED** For gene : {gene} iter : {iter} | top_particle_position: {best_particle_position}, top_particle_fitness: {best_particle_fitness}")

                else:
                    if np.random.random() <= 0.5:
                        #print(".....................")
                        #print("Expanded Exploitation")
                        #print(".....................")
                        if np.random.rand() < 0.5:
                            #print("AO")
                            all_positions = np.array([particle.particle_best_position for particle in particles])
                            mean_position = np.mean(all_positions, axis=0)
                            particle.current_position = (best_particle_position - mean_position) * alpha - np.random.rand(dimension) + ((x_max - x_min) * np.random.rand(dimension) + x_min) * delta
                        else:
                            #print("PSO")
                            particle.velocity = w * particle.velocity +accel_factor * r2 * (best_particle_position - particle.current_position)+accel_factor * r1 * (particle.particle_best_position - particle.current_position)
                            particle.current_position = particle.current_position + particle.velocity
                        if np.any(np.abs(particle.current_position) >= x_max):
                            particle.current_position = initialize(x_min, x_max, dimension)
                        fitness = RNN(data,gene,particle.current_position)
                        #print(f"New Position: {particle.current_position}, Fitness: {fitness}")
                        if particle.fitness > fitness:
                            particle.particle_best_position = particle.current_position
                            particle.fitness = fitness
                            #print(f"**UPDATED**Particle best Position: {particle.particle_best_position}, Fitness: {particle.fitness}")
                            if best_particle_fitness > particle.fitness:
                                best_particle_fitness = particle.fitness
                                best_particle_position = particle.particle_best_position.copy()
                                print(f"**CHANGED** For gene : {gene} iter : {iter} | top_particle_position: {best_particle_position}, top_particle_fitness: {best_particle_fitness}")
                    else:
                        #print(".....................")
                        #print("Narrowed Exploitation")
                        #print(".....................")
                        if np.random.rand() < 0.5:
                            #print("AO")
                            particle.current_position = QF * np.array(best_particle_position) - (G1 * np.array(particle.particle_best_position) * np.random.rand()) - G2 * levy(dimension) + np.random.rand() * G1
                        else:
                            #print("PSO")
                            particle.velocity = w * particle.velocity +accel_factor * r2 * (best_particle_position - particle.current_position)+accel_factor * r1 * (particle.particle_best_position - particle.current_position)
                            particle.current_position = particle.current_position + particle.velocity
                        if np.any(np.abs(particle.current_position) >= x_max):
                            particle.current_position = initialize(x_min, x_max, dimension)
                        fitness = RNN(data,gene,particle.current_position)
                        #print(f"New Position: {particle.current_position}, Fitness: {fitness}")
                        if particle.fitness > fitness:
                            particle.particle_best_position = particle.current_position
                            particle.fitness = fitness
                            #print(f"**UPDATED**Particle best Position: {particle.particle_best_position}, Fitness: {particle.fitness}")
                            if best_particle_fitness > particle.fitness:
                                best_particle_fitness = particle.fitness
                                best_particle_position = particle.particle_best_position.copy()
                                print(f"**CHANGED** For gene : {gene} iter : {iter} | top_particle_position: {best_particle_position}, top_particle_fitness: {best_particle_fitness}")
            fitness_per_gene[gene].append(best_particle_fitness)
            #print("Top Particle : ",best_particle_position)
            #print("Top Particle fitness :", best_particle_fitness)
        gene_gbest_position.append(best_particle_position)
    gbest_matrix = np.array(gene_gbest_position)

        # Extract the first N elements from each row to get an N x N matrix
    fij_matrix = gbest_matrix[:, :num_gene]
    return fij_matrix, fitness_per_gene


if __name__ == '__main__':
  num_gene = 8
  num_regulator = 4
  num_particle = math.comb(num_gene,num_regulator)
#  num_particle = 3
  max_iteration = 7000
  x_min = -1
  x_max = 1 
  for cur in range(0,2):
    np.random.seed(42+cur)
    random.seed(7+cur)
    result_matrix, fitness_per_gene = AO(num_gene, num_particle, num_gene+2, max_iteration,x_min,x_max)
    plot_fitness(fitness_per_gene, max_iteration,cur)
    csv_filename = "result_matrix"+"_"+str(max_iteration)+"_"+str(cur)+".csv"
    pd.DataFrame(result_matrix).to_csv(csv_filename, index=False)
    print(f"Results saved to {csv_filename}")
    print(f"Fitness evolution plot saved as fitness_evolution_APSO_V@2_{max_iteration}_{cur}.png")