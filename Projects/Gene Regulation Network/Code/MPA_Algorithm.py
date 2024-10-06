import numpy as np

class Prey:
    def __init__(self, position, fitness):
        self.position = position
        self.fitness = fitness

def initialize(x_min, x_max, dimension):
    position = x_min + np.random.uniform(0, 1, dimension) * (x_max - x_min)
    return position

def levy(dimenssion, alpha):
    x = np.random.randn(dimenssion)
    y = np.random.randn(dimenssion)
    denominator = np.abs(y) ** (1 / alpha)
    return x / denominator

def fitness_function(prey):
    return sum((prey - 1) ** 2)

def MPA(population, dimension, max_iteration,x_min,x_max):
    constant_P = 0.5
    Fish_Aggregating_Devices = 0.2
    all_prey = []
    top_preditor_position = np.zeros(dimension)
    top_preditor_fitness = float('inf')

    for _ in range(population):
        position = initialize(x_min, x_max, dimension)
        all_prey.append(Prey(position, float('inf')))

#..................................................................................
    # for prey in all_prey:
    #     print(f"Prey Position: {prey.position}, Fitness: {prey.fitness}")

    # print("Top Predator Position:", top_preditor_position)   
    # print("Best Fitness:", top_preditor_fitness)
#...................................................................................
    for iter in range(max_iteration):
        print("epoch : ", iter)
        print("................")
        CF = (1 - iter / max_iteration) ** (2 * iter / max_iteration)
        for prey in all_prey:
            fitness = fitness_function(prey.position)
            prey.fitness = fitness
            print(f"Prey Position: {prey.position}, Fitness: {prey.fitness}")
            if top_preditor_fitness > fitness:
                top_preditor_fitness = fitness
                top_preditor_position = prey.position.copy()
                print(f"**CHANGED** top_preditor_position: {top_preditor_position}, top_preditor_fitness: {top_preditor_fitness}")
        
        if iter < max_iteration / 3:
            print("First phase: Exploration phase")
            print("...............................")
            for prey in all_prey:
                print("position before:",prey.position)
                RBr = np.random.randn(dimension)
                print ("random position:",RBr)
                R = np.random.rand()
                step_size = RBr*(top_preditor_position-RBr*prey.position)
                prey.position += constant_P*R*step_size
                print("step Size :",step_size)
                print("position after:",prey.position)
        elif iter > max_iteration / 3 and iter < 2 * max_iteration / 3:
            print("Second phase: Transition phase between exploration phase and ex-ploitation phase")
            print("...................................................................................")
            for prey in all_prey[:len(all_prey) // 2]:
                print("***************************")
                print("Brownian Motion Exploration")
                print("****************************")
                print("position before:",prey.position)
                RLv = 0.05 * levy(dimension, 1.5)
                print ("random position:",RLv)
                step_size = RLv * (top_preditor_position - RLv * prey.position)
                prey.position += constant_P * R * step_size
                print("step Size :",step_size)
                print("position after:",prey.position)
            for prey in all_prey[len(all_prey)//2:]:
                print("************************")
                print("Levy Motion Exploration")
                print("************************")
                print("position before:",prey.position)
                RBr = np.random.randn(dimension)
                print ("random position:",RBr)
                R = np.random.rand()
                step_size = RBr* (RBr* top_preditor_position - prey.position)
                prey.position = top_preditor_position + constant_P * CF * step_size
                print("step Size :",step_size)
                print("position after:",prey.position)
        else:
            print(".............................")
            print("Third phase: Exploitation phase")
            print(".............................")
            print("position before:",prey.position)
            RLv = 0.05 * levy(dimension, 1.5)
            print ("random position:",RLv)
            step_size = RLv * (RLv* top_preditor_position - prey.position)
            prey.position = top_preditor_position + constant_P * CF * step_size
            print("step Size :",step_size)
            print("position after:",prey.position)
               
        for prey in all_prey:
            fitness = fitness_function(prey.position)
            prey.fitness = fitness
            print(f"Prey Position: {prey.position}, Fitness: {prey.fitness}")
            if top_preditor_fitness > fitness:
                top_preditor_fitness = fitness
                top_preditor_position = prey.position.copy()
        print("****************************************")
        print("Eddy formation with the effect from FADs") 
        print("****************************************") 
        if np.random.rand() < Fish_Aggregating_Devices:
            for prey in all_prey:
                U = np.random.rand(dimension) < Fish_Aggregating_Devices
                print("position before:",prey.position)
                prey.position += CF * ((x_min + np.random.rand(dimension) * (x_max - x_min)) * U)
                print("position after:",prey.position)
        else:
            for prey in all_prey:
                r = np.random.rand()
                Rs = population
                random_indices = np.random.permutation(Rs)
                print("position before:",prey.position)
                stepsize = (Fish_Aggregating_Devices * (1 - r) + r) * (all_prey[random_indices[0]].position - all_prey[random_indices[1]].position)
                prey.position += stepsize
                print("position after:",prey.position)
    return top_preditor_fitness,top_preditor_position
                 

#MPA(population, dimension, max_iteration,x_min,x_max)

top_preditor_fitness,top_preditor_position = MPA(10,8,4,-10,10)
print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
print("Final Top Predator Fitness:", top_preditor_fitness)
print("Final Top Predator Position:", top_preditor_position)
