import numpy as np
import pandas as pd
from typing import List, Tuple
import random
import math
import matplotlib.pyplot as plt

class Particle:
    def __init__(self, position: np.ndarray, velocity: np.ndarray = None):
        """Initialize a particle for BAPSO algorithm.
        
        Args:
            position: Initial position array containing [fij, δi, εi, μi]
            velocity: Initial velocity (set to None/empty by default per BAPSO)
        """
        self.position = position
        self.velocity = velocity
        self.personal_best_position = position.copy()
        self.personal_best_fitness = float('inf')
        
class BAPSO:
    def __init__(self, 
                num_genes: int,
                time_points: int,
                swarm_size: int,
                max_iterations: int,
                c1: float = 2.0,
                c2: float = 2.0):
        """Initialize BAPSO for Half-System GRN inference.
        
        Args:
            num_genes: Number of genes in the network
            time_points: Number of time points in expression data
            swarm_size: Size of particle swarm
            max_iterations: Maximum iterations for optimization
            c1: Cognitive component coefficient
            c2: Social component coefficient
        """
        self.num_genes = num_genes
        self.time_points = time_points
        self.swarm_size = swarm_size
        self.max_iterations = max_iterations
        self.c1 = c1
        self.c2 = c2
        
        # Dimension per gene: N fij parameters + δi + εi + μi
        self.dim_per_gene = num_genes + 3
        
        # Store fitness history for plotting
        self.fitness_history = []
        
    def initialize_swarm(self) -> List[Particle]:
        """Initialize particle swarm for a single gene."""
        swarm = []
        for _ in range(self.swarm_size):
            # Initialize fij between -1 and 1
            fij = np.random.uniform(-1, 1, self.num_genes)
            
            # Initialize rate constants and parameters (positive values)
            delta_i = np.random.uniform(0, 1)  # Rate constant
            epsilon_i = np.random.uniform(0, 1)  # Self-degradation
            mu_i = np.random.uniform(0, 1)      # Error correction
            
            position = np.concatenate([fij, [delta_i, epsilon_i, mu_i]])
            
            # Initialize velocity as empty array (BAPSO specific)
            particle = Particle(position=position, velocity=None)
            swarm.append(particle)
            
        return swarm
    
    def calculate_half_system(self, 
                          gene_data: np.ndarray,
                          particle: Particle,
                          delta_t: float = 1.0) -> np.ndarray:
        """Calculate predicted expression using Half-System formulation."""
        predicted = np.zeros(self.time_points)
        predicted[0] = gene_data[0, -1]  # Initialize with first timepoint
        
        fij = particle.position[:self.num_genes]
        delta_i = particle.position[-3]
        epsilon_i = particle.position[-2]
        mu_i = particle.position[-1]
        
        for t in range(1, self.time_points):
            # Calculate production term
            prod_term = delta_i * np.prod(np.power(gene_data[t-1, :-1], fij))
            
            # Calculate current expression
            current_expr = gene_data[t-1, -1]
            
            # Calculate prediction error for error correction term
            if t > 1:
                d_i = gene_data[t-1, -1] - predicted[t-1]
            else:
                d_i = 0
                
            # Update expression using modified half-system equation
            predicted[t] = (delta_t * prod_term + 
                          (1 - delta_t * epsilon_i) * current_expr + 
                          mu_i * d_i)
            
        return predicted
    
    def calculate_fitness(self, 
                       predicted: np.ndarray, 
                       actual: np.ndarray) -> float:
        """Calculate fitness (MSE) between predicted and actual expression."""
        return np.mean((predicted - actual) ** 2)
    
    def update_velocity(self,
                     particle: Particle,
                     global_best: np.ndarray,
                     iteration: int) -> np.ndarray:
        """Update particle velocity using BAPSO formulation."""
        if iteration == 0:
            return None  # First iteration has no velocity (BAPSO specific)
            
        r0 = np.random.random()  # Random inertia weight (BAPSO specific)
        r1 = np.random.random()
        r2 = np.random.random()
        
        if particle.velocity is None:
            particle.velocity = np.zeros_like(particle.position)
            
        new_velocity = (r0 * particle.velocity +
                       self.c1 * r1 * (particle.personal_best_position - particle.position) +
                       self.c2 * r2 * (global_best - particle.position))
        
        return new_velocity
    
    def infer_single_gene(self, 
                       expression_data: np.ndarray,
                       target_gene_idx: int) -> Tuple[np.ndarray, float]:
        """Infer regulations for a single target gene."""
        # Prepare data - move target gene to last column
        gene_data = expression_data.copy()
        gene_data = np.column_stack([
            np.delete(gene_data, target_gene_idx, axis=1),
            gene_data[:, target_gene_idx]
        ])
        
        # Initialize swarm
        swarm = self.initialize_swarm()
        global_best_position = None
        global_best_fitness = float('inf')
        iteration_fitness = []
        
        # Main BAPSO loop
        for iteration in range(self.max_iterations):
            for particle in swarm:
                # Calculate fitness
                predicted = self.calculate_half_system(gene_data, particle)
                fitness = self.calculate_fitness(predicted, gene_data[:, -1])
                
                # Update personal best
                if fitness < particle.personal_best_fitness:
                    particle.personal_best_fitness = fitness
                    particle.personal_best_position = particle.position.copy()
                    
                # Update global best
                if fitness < global_best_fitness:
                    global_best_fitness = fitness
                    global_best_position = particle.position.copy()
            
            # Store best fitness for this iteration
            iteration_fitness.append(global_best_fitness)
            
            # Update particles
            for particle in swarm:
                # Update velocity using BAPSO
                particle.velocity = self.update_velocity(
                    particle, global_best_position, iteration
                )
                
                if particle.velocity is not None:  # Skip first iteration
                    # Update position
                    particle.position = particle.position + particle.velocity
                    
                    # Constrain fij parameters
                    particle.position[:self.num_genes] = np.clip(
                        particle.position[:self.num_genes], -1, 1
                    )
                    
                    # Ensure rate constants remain positive
                    particle.position[-3:] = np.abs(particle.position[-3:])
        
        self.fitness_history.append(iteration_fitness)
        return global_best_position, global_best_fitness
    
    def infer_network(self, expression_data: np.ndarray) -> np.ndarray:
        """Infer the complete gene regulatory network."""
        network_matrix = np.zeros((self.num_genes, self.num_genes))
        
        for target_gene in range(self.num_genes):
            best_position, _ = self.infer_single_gene(
                expression_data, target_gene
            )
            
            # Extract fij values from best position
            network_matrix[target_gene] = best_position[:self.num_genes]
            
        return network_matrix

def plot_fitness(bapso: BAPSO, run_number: int):
    """Plot fitness evolution for each gene."""
    plt.figure(figsize=(12, 8))
    for gene, fitness_values in enumerate(bapso.fitness_history):
        plt.plot(range(len(fitness_values)), fitness_values, label=f'Gene {gene+1}')
    
    plt_filename = f'bapso_fitness_evolution_{run_number}.png'
    plt.xlabel('Iteration')
    plt.ylabel('Fitness (MSE)')
    plt.title('BAPSO Fitness Evolution per Gene')
    plt.legend()
    plt.yscale('log')
    plt.grid(True)
    plt.tight_layout()
    plt.savefig(plt_filename)
    plt.close()

def process_multiple_datasets(bapso: BAPSO,
                            datasets: List[np.ndarray],
                            num_runs: int = 10,
                            inclusion_threshold: float = 0.5) -> np.ndarray:
    """Process multiple datasets to create final network structure."""
    dataset_networks = []
    
    # Process each dataset
    for dataset in datasets:
        run_networks = []
        
        # Multiple independent runs
        for _ in range(num_runs):
            network = bapso.infer_network(dataset)
            run_networks.append(network)
            
        # Calculate plausibility scores
        plausibility_scores = np.mean(run_networks, axis=0)
        
        # Create binary network using threshold
        binary_network = (plausibility_scores >= inclusion_threshold).astype(int)
        dataset_networks.append(binary_network)
    
    # Calculate final inclusion scores
    inclusion_scores = np.mean(dataset_networks, axis=0)
    
    # Create final network structure
    final_network = (inclusion_scores >= inclusion_threshold).astype(int)
    
    return final_network

if __name__ == "__main__":
    # Read the data
    data = pd.read_csv('Exp_1.csv', sep=',')
    expression_data = data.iloc[:, 1:].values  # Assume first column is time
    
    # Parameters
    num_genes = expression_data.shape[1]
    time_points = expression_data.shape[0]
    swarm_size = 50
    max_iterations = 1000
    
    # Run multiple times with different seeds
    for cur in range(10):
        print(f"\nStarting run {cur + 1}/10")
        
        # Set seeds
        np.random.seed(42 + cur)
        random.seed(7 + cur)
        
        # Initialize BAPSO
        bapso = BAPSO(
            num_genes=num_genes,
            time_points=time_points,
            swarm_size=swarm_size,
            max_iterations=max_iterations
        )
        
        # Create dataset list (using single dataset multiple times for example)
        datasets = [expression_data for _ in range(4)]
        
        # Infer network
        final_network = process_multiple_datasets(
            bapso=bapso,
            datasets=datasets,
            num_runs=10,
            inclusion_threshold=0.5
        )
        
        # Plot fitness evolution
        plot_fitness(bapso, cur)
        
        # Save results
        csv_filename = f"bapso_result_matrix_{max_iterations}_{cur}.csv"
        pd.DataFrame(final_network).to_csv(csv_filename, index=False)
        
        print(f"Results saved to {csv_filename}")
        print(f"Fitness evolution plot saved as bapso_fitness_evolution_{cur}.png")
