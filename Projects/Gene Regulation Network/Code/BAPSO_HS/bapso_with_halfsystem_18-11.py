import numpy as np
import random
import math
import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv('Exp_1.csv', sep=',')

class Particle:
  def __init__(self,position,velocity,fitness):
    self.position = position
    self.velocity  = velocity
    self.local_best_position = position.copy()
    self.fitness = fitness
  def update_local_best(self,local_best_position_new):
    self.local_best_position = local_best_position_new.copy()
  def update_fitness(self,fitness):
    self.fitness = fitness

# Define the modified fitness function to calculate MSE
def fitness_function(array1,array2):
    # Compute the squared error for each element
    squared_error = np.mean((array1-array2) ** 2)
    return squared_error

def HS(data,gene_index,position):
   gene_predicted = []
   delta_t = 6
   fij= position[:8]
   delta_i = position[-3]
   epsilon_i = position[-2]
   mu_i = position[-1]
   gene_predicted.append(data.iloc[0].values[gene_index+1])
   for t in range(1,len(data)):
      gene_tp = data.iloc[t].values
      gene_tp_1 = gene_tp[1:]
      expression = np.power(gene_tp_1,fij)
      expression = [expression[i] if expression[i]!= 0 and expression[i]!= float('inf') else 1 for i in range(len(expression))]
      prod = np.prod(expression)
      prod_term = delta_i * prod
      d_i = np.abs(data.iloc[t-1].values[gene_index+1] - gene_predicted[-1])
      gene_predicted.append(delta_t*prod_term+(1-(delta_t*epsilon_i))*gene_tp_1[gene_index]+mu_i*d_i)
   gene_actual_data = data.iloc[:, gene_index+1]
   gene_actual = gene_actual_data.to_numpy()
  #  fitness = (1/T)  * sum((column_array - gene_predicted)**2)
   fitness = fitness_function(gene_actual,np.array(gene_predicted))
   return fitness 
      

def plot_fitness(fitness_per_gene, max_iteration,cur):
    plt.figure(figsize=(12, 8))
    print((np.sum(fitness_per_gene, axis=0))/8)
    MSE = np.array((np.sum(fitness_per_gene, axis=0))/8)
    plt.plot(range(max_iteration), MSE)
    plt_filename = 'fitness_evolution_BAPSO_mu_i_d_i'+str(cur)+".png"
    plt.xlabel('Iteration')
    plt.ylabel('Fitness (MSE)')
    plt.title('Fitness Evolution per Gene')
    plt.legend()
    plt.yscale('log')  # Use log scale for y-axis to better visualize improvements
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(plt_filename)
    plt.close()

def BAPSO(num_gene,num_particle,max_iteration,max_inertia,min_inertia,accel_factor):
    # Store all gbest for all gene
    gene_gbest_position =[]
    fitness_per_gene = [[] for _ in range(num_gene)]
    for gene in range(num_gene):
      particles = []    # Store all the particles
      swarm_global_best_position = None  # Initialization of global best
      swarm_best_fitness = float('inf')  # Initialization of fitness
      for population in range(num_particle):
        # Initialize position, velocity, and fitness for each particle
        fij = np.random.uniform(-1, 1,num_gene)    
        # Initialize rate constants and parameters (positive values)
        delta_i = np.random.uniform(0, 1)  # Rate constant
        epsilon_i = np.random.uniform(0, 1)  # Self-degradation
        while(epsilon_i == 0):
           epsilon_i = np.random.uniform(0, 1)
        mu_i = np.random.uniform(0, 1)      # Error correction
            
        position = np.concatenate([fij, [delta_i, epsilon_i, mu_i]])
        velocity = np.zeros(num_gene+3)
        fitness = HS(data,gene,position)
        #print(f'for gene {gene} for population {population} initial fitness is {fitness} \n')
        #print(f'predicted value for popution{ population} is {predicted}')
        # Update global best according
        if fitness < swarm_best_fitness:
            swarm_best_fitness = fitness
            swarm_global_best_position = position.copy()
        particles.append(Particle(position,velocity,fitness))
      fitness_per_gene[gene].append(swarm_best_fitness)
      for itr in range(max_iteration-1):
        w = np.random.uniform(0,1)
        r1 = random.random()
        r2 = random.random()
        for index,particle in enumerate(particles):
          #Update Velocity
          particle.velocity = (w * particle.velocity +
                                 accel_factor * r1 * (particle.local_best_position - particle.position) +
                                 accel_factor * r2 * (swarm_global_best_position - particle.position))

            # Update position
          particle.position += particle.velocity
          #If particle value exceed the range then reinitialize
          if np.any(np.abs(particle.position) >= 1 ) or np.any(particle.position[8:] < 0)  or particle.position[9] <= 0:
              fij = np.random.uniform(-1, 1,num_gene)    
              # Initialize rate constants and parameters (positive values)
              delta_i = np.random.uniform(0, 1)  # Rate constant
              epsilon_i = np.random.uniform(0, 1)  # Self-degradation
              while(epsilon_i == 0):
                epsilon_i = np.random.uniform(0, 1)
              mu_i = np.random.uniform(0, 1)      # Error correction
            
              particle.position = np.concatenate([fij, [delta_i, epsilon_i, mu_i]])
              particle.velocity = np.zeros(num_gene+3)
          if(particle.position[9] == 0):
            while(particle.position[9] == 0):
                particle.position[9] = np.random.uniform(0, 1)
          #Calculate fitness using RNN
          fitness = HS(data,gene,particle.position)
          if fitness < particle.fitness:
            particle.update_fitness(fitness)
            particle.update_local_best(particle.position)
          if fitness < swarm_best_fitness:
            swarm_best_fitness = fitness
            swarm_global_best_position = particle.position.copy()
            print(f"population best update for gene : {gene} iteration {itr} population: {index}.\n New global best is : {swarm_global_best_position}\n")
        fitness_per_gene[gene].append(swarm_best_fitness)
      gene_gbest_position.append(swarm_global_best_position);
    gbest_matrix = np.array(gene_gbest_position)
    # Extract the first N elements from each row to get an N x N matrix
    fij_matrix = gbest_matrix[:, :num_gene]
    return fij_matrix, fitness_per_gene

if __name__ == '__main__':
  num_gene = 8
  num_regulator = 4
  num_particle = math.comb(num_gene,num_regulator)
#  num_particle = 3
  max_iteration = 1000
  max_inertia = 0.9
  min_inertia = 0.4
  accel_factor = 2  # c1 = c2 = 2
  for cur in range(0,1):
    np.random.seed(42+cur)
    random.seed(7+cur)
    result_matrix, fitness_per_gene = BAPSO(num_gene, num_particle, max_iteration, max_inertia, min_inertia, accel_factor)
    plot_fitness(fitness_per_gene, max_iteration,cur)
    csv_filename = "result_matrix_BAPSO"+"_"+str(max_iteration)+"_"+str(cur)+".csv"
    pd.DataFrame(result_matrix).to_csv(csv_filename, index=False)
    print(f"Results saved to {csv_filename}")
    print(f"Fitness evolution plot of BAPSO saved as fitness_evolution_{cur}.png")