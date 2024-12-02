from itertools import combinations
import math
import random
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import time



def BAPSO(data,num_gene,num_regulator,population_size,combination,Iteration):
    # lower_bound = -1
    # upper_bound = 1
    fitness_curve = np.zeros((num_gene,Iteration))
    total_timepoints = data.shape[0]
    delta_t = 6
    positions = np.zeros((num_gene, population_size, num_regulator+3))
    velocities = np.zeros((num_gene, population_size, num_regulator+3))
    fitness = np.zeros((population_size))
    best_fitness = None
    global_best_position = None
    local_best_position = np.zeros((population_size, num_regulator+3))
    result_matrix = np.zeros((8,8))
    all_global_best_position = np.zeros((8,7))
    for gene in range(num_gene):
        print(f"For gene : {gene}")
        positions[gene,:,:4] = np.random.uniform(-1,1,(population_size,4))
        positions[gene,:,4:] = np.random.uniform(0,1,(population_size,3))
        # #print(f"Positions : {positions[gene]} Length : {len(positions[gene])}")
        # #print(f"velocities : {velocities[gene]} Length : {len(positions[gene])}")
        
        new_data = np.zeros((total_timepoints,population_size,num_regulator))
        # #print(total_timepoints)
        for tp in range(total_timepoints):
            for pop in range(population_size):
                new_data[tp,pop,:] = data[tp,combination[pop]]
        # #print(new_data)    
        predicted = np.zeros((49,70))
        
        for tp in range(total_timepoints):
            if tp == 0:
                predicted[tp] = np.tile(data[tp,gene],(1,70))
            else:
                expression = new_data[tp]
                #print("expression data : ", expression.shape)
                expression = np.where(expression != 0, expression, 1)
                #print("positions",positions[gene,:,:].shape)
                #print("positions at one",positions[gene,:,6].shape)
                expression = np.power(expression,positions[gene,:,:4])
                #print(f"expression : {expression.shape}")
                product = np.prod(expression,axis=1)
                #print("predictedtp - 1", predicted[tp-1].shape)
                #print("data[tp-1,gene]", data[tp-1,gene].shape)
                di = np.abs(np.tile(data[tp-1,gene],(1,70)) - predicted[tp-1])
                #print(f"di : {di.shape}")
                #print(f"product : {product.shape}")
                predicted[tp] = (delta_t*(positions[gene,:,4]*product))+(1 - delta_t*positions[gene,:,5])*data[tp-1,gene] + positions[gene,:,6]*di
                #print(f"predicted : {predicted[tp].shape}")
        data_transp = np.tile(data[:,gene],(70,1))
        data_transp = np.transpose(data_transp)
        #print("transp_data",data_transp.shape)
        #print("predicted_data",predicted.shape)
        _temp_fitness = np.mean(((predicted - data_transp))**2,axis=0)
        #print(_temp_fitness.shape)
        # #print("temp_fitness",_temp_fitness)
        fitness = _temp_fitness.copy()
        #print("fitness",fitness.shape)
        best_fitness = np.min(fitness).copy()
        local_best_position = positions[gene,:,:].copy()
        global_best_position = local_best_position[np.argmin(fitness)].copy()
        # #print("local_best_positions",local_best_position)
        # #print("fitness",fitness)
        # #print("fitness = ",_temp_fitness, "len = ",_temp_fitness.shape[1])
        # #print(f"Fitnesses : {fitness}")
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        print(f"Particle at Index : {np.argmin(fitness)} and particle position : {positions[gene,np.argmin(fitness),:]} fitness : {fitness[np.argmin(fitness)]}")
        c1 = c2 = 2
        w = np.random.uniform(0,1,(70,1))
        r1 = np.random.uniform(0,1,(70,1))
        r2 = np.random.uniform(0,1,(70,1))
        for iter in range(Iteration):
            # #print(f"For Gene {gene} For Epoch : {iter}")
            global_best_position_matrix = np.broadcast_to(global_best_position,(70,7))
            # #print(f'Global_best_position_matrix{global_best_position_matrix} Length : {global_best_position_matrix.shape[0]}')
            # if np.any(np.isnan(velocities[gene,:,:])) or np.any(np.isinf(velocities[gene,:,:])):
            #     print("NaN or Inf detected in velocities")
            # if np.any(np.isnan(local_best_position)) or np.any(np.isinf(local_best_position)):
            #     print("NaN or Inf detected in local_best_position")
            # if np.any(np.isnan(global_best_position)) or np.any(np.isinf(global_best_position)):
            #     print("NaN or Inf detected in global_best_position")
            # if np.any(np.isnan(positions[gene,:,:])) or np.any(np.isinf(positions[gene,:,:])):
            #     print("NaN or Inf detected in positions")
            velocities[gene,:,:] = w * velocities[gene,:,:] + c1*r1*(local_best_position - positions[gene,:,:]) + c2*r2*(global_best_position_matrix - positions[gene,:,:])
            positions[gene,:,:] = positions[gene,:,:] + velocities[gene,:,:]
            for pop in range(population_size):
                # #print(f"Positions : {positions[gene,pop,:]}")
                # #print(f"velocities : {velocities[gene,pop,:]}")
                if np.any(np.abs(positions[gene,pop,:]) >= 1) or np.any(positions[gene,pop,4:] <= 0):
                            positions[gene,pop,:4] = np.random.uniform(-1,1,(1,4))
                            positions[gene,pop,4:] = np.random.uniform(0,1,(1,3))
                            velocities[gene,pop,:] = np.zeros(num_regulator+3)
                            # #print(f"Updated particle : {pop}")
            for tp in range(total_timepoints):
                if tp == 0:
                    predicted[tp] = np.tile(data[tp,gene],(1,70))
                else:
                    expression = new_data[tp]
                    expression = np.where(expression != 0, expression, 1)
                    expression = np.power(expression,positions[gene,:,:4])
                    # #print(f"expression : {expression}")
                    product = np.prod(expression,axis=1)
                    di = np.abs(np.tile(data[tp-1,gene],(1,70)) - predicted[tp-1])
                    # #print(f"product : {product}")
                    predicted[tp] = (delta_t*(positions[gene,:,4]*product))+(1 - delta_t*positions[gene,:,5])*data[tp-1,gene] + positions[gene,:,6]*di
            data_transp = np.tile(data[:,gene],(70,1))
            data_transp = np.transpose(data_transp)
            # #print(data_transp)
            _temp_fitness = np.mean(((predicted - data_transp))**2,axis=0)
            # #print(_temp_fitness.shape)
            for pop in range(population_size):
                if _temp_fitness[pop] < fitness[pop]:
                    fitness[pop] = _temp_fitness[pop].copy()
                    local_best_position[pop] = positions[gene,pop,:].copy()
            if best_fitness > np.min(fitness):
                best_fitness = np.min(fitness).copy()
                global_best_position = local_best_position[np.argmin(fitness)].copy()
                # print(f"******Epoch {iter} : Index : {np.argmin(fitness)} Updated Global Best Fitness : {best_fitness}*******")
                # print(f"******Updated Global Best Position : {global_best_position}*******")
        # #print("fitness = ",_temp_fitness, "len = ",_temp_fitness.shape[1])
        # #print(f"Fitnesses : {fitness}")
            fitness_curve[gene,iter] = best_fitness.copy()
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        print(f"Particle at Index : {np.argmin(fitness)} and particle position : {positions[gene,np.argmin(fitness),:]} fitness : {fitness[np.argmin(fitness)]}")
        print(f"best combination {combination[np.argmin(fitness)]} at Index : {np.argmin(fitness)}")
        best_combination = combination[np.argmin(fitness)].copy()
        all_global_best_position[gene,:] = global_best_position
        for index, combo in enumerate(best_combination):
            result_matrix[gene,combo] = global_best_position[index]
    return result_matrix,all_global_best_position, fitness_curve
if __name__ == '__main__':
    #start of execution
    start_time = time.time()
    
    data = pd.read_csv('FINAL_YEAR_PROJECT/GRN_Code/Exp_1.csv', sep=',')
    data = np.array(data)
    num_gene = data.shape[1] - 1
    genes = list(range(num_gene))
    num_regulator = 4
    combination = np.array(list(combinations(genes, num_regulator)))
    population_size = combination.shape[0]
    Iteration = 10000
    for cur in range (10):
        # #print(f"population_size : {population_size}")
        # #print(f"Number of gene : {num_gene}")
        # #print(f"Gene Expression : {data[:,1:]}")
        # #print(f"Combination : {combination}")
        result_matrix,all_global_best_position, fitness_curve = BAPSO(data[:,1:],num_gene,num_regulator,population_size,combination,Iteration)
        #print(f"Result Matrix : {result_matrix}")
        #print(f"All global best position {all_global_best_position}")
        #calculating execution time
        print("--- %s seconds ---" % (time.time() - start_time))
        csv_filename = "FINAL_YEAR_PROJECT/GRN_Code/BAPSO_HS_NEW/result_matrix_HS_BAPSO"+"_"+str(cur)+"_"+str(Iteration)+"_"+".csv"
        pd.DataFrame(result_matrix).to_csv(csv_filename, index=False)
        iteration = np.arange(1, Iteration+1)
        plt.figure(figsize= (15,8))
        mean_fitness_curve = np.mean(fitness_curve, axis = 0)
        #print(fitness_curve.shape)
        #print(mean_fitness_curve.shape)
        plt.subplot(1,2,1)
        plt.plot(iteration,mean_fitness_curve, label = "MSE")
        plt.legend()
        plt.xlabel("Iteration")
        plt.ylabel("MSE")
        plt.yscale('log')
        plt.title("MSE CURVE")
        plt.grid(True)
        plt.subplot(1,2,2)
        plt.xlabel("Iteration")
        plt.ylabel("SE per gene ")
        plt.yscale('log')
        plt.title("SE CURVE PER GENE")
        for gene in range(num_gene):
            plt.plot(iteration,fitness_curve[gene,:], label = f"Gene :{gene}")
            plt.legend()
        image_file_name = "FINAL_YEAR_PROJECT/GRN_Code/BAPSO_HS_NEW/MSE_Curve_HS_BAPSO"+str(cur)+"_"+str(Iteration)+".png"
        plt.savefig(image_file_name)



