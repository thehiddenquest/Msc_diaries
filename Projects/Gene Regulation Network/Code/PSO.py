import numpy as np
import random

# Define the Particle class
class Particle:
    def __init__(self, position,velocity):
        self.position = position
        self.velocity = velocity 
        self.best_position = position
        self.best_fitness = float('inf')

# Define the fitness function
def fitness_function(x):
    return 10 * (x - 1)  # F(x) = 10 * (x - 1)

# PSO Algorithm
def PSO(num_particles, max_inertia, min_inertia, num_vars, accel_factor, max_iterations):
    # Initialize particles and variables
    particles = []
    swarm_best_position = None
    swarm_best_fitness = float('inf')
    best_particle_index = None  # To track the index of the best particle

    # Create position matrix (random values between (0, 1])
    print("\nInitial Position Matrix:")
    for i in range(num_particles):
        position = np.random.uniform(0, 1, num_vars)  # Each particle has 1 dimension
        velocity =  np.random.uniform(0, 1, num_vars)
        particle = Particle(position,velocity)
        particles.append(particle)

        # Print initial position and velocity
        print(f"Particle {i+1}: Initial Position = {particle.position}, Initial Velocity = {particle.velocity}")

        # Evaluate initial fitness
        fitness = fitness_function(particle.position)
        print(f"Particle {i+1}: Initial Fitness = {fitness}")

        if fitness < swarm_best_fitness:
            swarm_best_fitness = fitness
            swarm_best_position = particle.position
            best_particle_index = i + 1  # Track the best particle index (1-based)

        particle.best_position = particle.position
        particle.best_fitness = fitness

    print(f"\nInitial Global Best Position = {swarm_best_position}")
    print(f"Initial Global Best Fitness = {swarm_best_fitness}\n")

    # PSO Main Loop
    for itr in range(max_iterations):
        # Dynamically adjust inertia
        w = max_inertia - (max_inertia - min_inertia) * itr / max_iterations

        for particle in particles:
            # Update velocity using inertia dampening formula
            r1 = random.random()
            r2 = random.random()

            particle.velocity = (w * particle.velocity + 
                                 accel_factor * r1 * (particle.best_position - particle.position) + 
                                 accel_factor * r2 * (swarm_best_position - particle.position))

            # Update position
            particle.position += particle.velocity

            # Evaluate fitness
            fitness = fitness_function(particle.position)

            # Update personal best (pBest)
            if fitness < particle.best_fitness:
                particle.best_fitness = fitness
                particle.best_position = particle.position

            # Update global best (gBest)
            if fitness < swarm_best_fitness:
                swarm_best_fitness = fitness
                swarm_best_position = particle.position
                best_particle_index = particles.index(particle) + 1  # Track the best particle index (1-based)

    # Return the global best particle, its index, position and velocity matrices
    best_particle = None
    for particle in particles:
        if np.all(particle.best_position == swarm_best_position):
            best_particle = particle
            break

    return best_particle, best_particle_index, [p.position for p in particles], [p.velocity for p in particles]

# Parameters
num_particles = 4
max_inertia = 0.9
min_inertia = 0.4
num_vars = 1  # Search space dimension (1 variable)
accel_factor = 2  # c1 = c2 = 2
max_iterations = 2 ** num_particles  # Number of iterations

# Run PSO
best_particle, best_particle_index, position_matrix, velocity_matrix = PSO(num_particles, max_inertia, min_inertia, num_vars, accel_factor, max_iterations)

# Output the results
print(f"\nBest Particle: Particle {best_particle_index}, Position = {best_particle.position}, Velocity = {best_particle.velocity}")
print(f"Position Matrix: {position_matrix}")
print(f"Velocity Matrix: {velocity_matrix}")