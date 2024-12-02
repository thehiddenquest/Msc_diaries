import numpy as np
import random
import math
import pandas as pd
import matplotlib.pyplot as plt
import itertools
import os

data = pd.read_csv('Dataset/Exp_1.csv', sep=',')

class Particle:
  def __init__(self,position,velocity,fitness):
    self.position = position.copy()
    self.velocity  = velocity.copy()
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

def HS(data,gene_index,position,num_gene):
   delta_t = 6
   fij= position[:4]
   delta_i = position[-3]
   epsilon_i = position[-2]
   mu_i = position[-1]
   genes = list(range(num_gene))
   combinations = itertools.combinations(genes, 4)
   fitness = float('inf')
   matrix = np.zeros(num_gene)

   for combo in combinations:
      gene_predicted = []
      gene_predicted.append(data.iloc[0].values[gene_index+1])
      for t in range(1,len(data)):
        gene_tp = data.iloc[t].values
        gene_tp_1 = gene_tp[1:]
        gene_regulator_expression = [gene_tp_1[i] for i in combo]
#        expression = np.power(gene_regulator_expression,fij)
        expression = np.where(gene_regulator_expression == 0, 1, np.power(gene_regulator_expression, fij))
        expression = [expression[i] if expression[i]!= 0 and expression[i]!= float('inf') else 1 for i in range(len(expression))]
        prod = np.prod(expression)
        prod_term = delta_i * prod
        d_i = np.abs(data.iloc[t-1].values[gene_index+1] - gene_predicted[-1])
        gene_predicted.append(delta_t*prod_term+(1-(delta_t*epsilon_i))*gene_tp_1[gene_index]+mu_i*d_i)
      gene_actual_data = data.iloc[:, gene_index+1]
      gene_actual = gene_actual_data.to_numpy()
    #  fitness = (1/T)  * sum((column_array - gene_predicted)**2)
      fitness1 = fitness_function(gene_actual,np.array(gene_predicted))
      if(fitness1<fitness):
        fitness = fitness1
        matrix = np.zeros(num_gene)  # Reset matrix to zero
        for idx, gene_idx in enumerate(combo):
            matrix[gene_idx] = fij[idx]
   return fitness,matrix
      

def plot_fitness(fitness_per_gene, max_iteration,cur,output_folder,num_gene):
    plt.figure(figsize=(12, 8))
    # Calculate the average fitness per gene
    average_fitness = (np.sum(fitness_per_gene, axis=0)) / num_gene
    # Print using an f-string
    print(f"Average fitness per gene: {average_fitness}")
    MSE = np.array(average_fitness)
    plt.plot(range(max_iteration), MSE)
    plt_filename = output_folder+'/fitness_evolution_'+str(cur)+".png"
    plt.xlabel('Iteration')
    plt.ylabel('Fitness (MSE)')
    plt.title('Fitness Evolution per Gene')
    plt.yscale('log')  # Use log scale for y-axis to better visualize improvements
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(plt_filename)
    plt.close()

def BAPSO(num_gene,num_particle,max_iteration,accel_factor):
    # Store all gbest for all gene
    gene_gbest_position =[]
    fitness_per_gene = [[] for _ in range(num_gene)]
    result_matrix_gene = []
    for gene in range(num_gene):
      particles = []    # Store all the particles
      swarm_global_best_position = None  # Initialization of global best
      swarm_best_fitness = float('inf')  # Initialization of fitness
      result_matrix_per_gene = None
      for _ in range(num_particle):
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
        fitness,matrix = HS(data,gene,position,num_gene)
        #print(f'for gene {gene} for population {population} initial fitness is {fitness} \n')
        #print(f'predicted value for population{ population} is {predicted}')
        # Update global best according
        if fitness < swarm_best_fitness:
            swarm_best_fitness = fitness
            swarm_global_best_position = position.copy()
            result_matrix_per_gene = matrix.copy()
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
          if np.any(np.abs(particle.position) >= 1 ) or np.any(particle.position[8:] < 0)  or particle.position[9] == 0:
              fij = np.random.uniform(-1, 1,num_gene)    
              # Initialize rate constants and parameters (positive values)
              delta_i = np.random.uniform(0, 1)  # Rate constant
              epsilon_i = np.random.uniform(0, 1)  # Self-degradation
              while(epsilon_i == 0):
                epsilon_i = np.random.uniform(0, 1)
              mu_i = np.random.uniform(0, 1)      # Error correction
            
              particle.position = np.concatenate([fij, [delta_i, epsilon_i, mu_i]])
              particle.velocity = np.zeros(num_gene+3)
          #Calculate fitness using RNN
          fitness,matrix = HS(data,gene,particle.position,num_gene)
          if fitness < particle.fitness:
            particle.update_fitness(fitness)
            particle.update_local_best(particle.position)
            if fitness < swarm_best_fitness:
              swarm_best_fitness = fitness
              swarm_global_best_position = particle.position.copy()
              result_matrix_per_gene = matrix.copy()
              print(f"population best update for gene : {gene} iteration {itr} population: {index}.\n New global best is : {swarm_global_best_position}\n")
        fitness_per_gene[gene].append(swarm_best_fitness)
      result_matrix_gene.append(result_matrix_per_gene)
      gene_gbest_position.append(swarm_global_best_position);
    gbest_matrix = np.array(gene_gbest_position)
    # Extract the first N elements from each row to get an N x N matrix
    fij_matrix = gbest_matrix[:, :num_gene]
    return fij_matrix, fitness_per_gene,result_matrix_gene

if __name__ == '__main__':
  num_gene = 8
  num_regulator = 4
  num_particle = math.comb(num_gene,num_regulator)
#  num_particle = 3
  max_iteration = 5000
  accel_factor = 2  # c1 = c2 = 2
  for cur in range(0,1):
    np.random.seed(42+cur)
    random.seed(7+cur)
    result_matrix, fitness_per_gene,resultant_matrix = BAPSO(num_gene, num_particle, max_iteration, accel_factor)
    
    
    # Get the full path of the currently running script
    script_path = __file__
    # Extract the script's name (filename only, no directory)
    script_name = os.path.basename(script_path)
    # Create a new directory with the script's name
    output_folder = "Output/"+script_name+"_"+str(max_iteration)
    os.makedirs(output_folder, exist_ok=True)  # Creates the folder if it doesn't exist
    

    plot_fitness(fitness_per_gene, max_iteration,cur,output_folder,num_gene)
    
    
    csv_filename = output_folder+"/result_matrix"+"_"+str(max_iteration)+"_"+str(cur)+".csv"
    pd.DataFrame(result_matrix).to_csv(csv_filename, index=False)

    csv_filename_resultant = output_folder+"/resultant_matrix"+"_"+str(max_iteration)+"_"+str(cur)+".csv"
    pd.DataFrame(resultant_matrix).to_csv(csv_filename_resultant, index=False)
    
    
    print(f"Results saved to {csv_filename}")
    print(f"Fitness evolution plot of BAPSO saved as fitness_evolution_{cur}.png")
    print(f"Results saved to {csv_filename_resultant}")