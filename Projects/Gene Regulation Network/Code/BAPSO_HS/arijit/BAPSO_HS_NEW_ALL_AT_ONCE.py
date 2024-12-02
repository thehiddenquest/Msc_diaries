from itertools import combinations
import math
import random
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import time



def BAPSO(data,num_gene,num_regulator,population_size,combination,Iteration):
    lb = -1
    ub = 1
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
        positions[gene,:,:4] = lb + (ub - lb)*np.random.rand(population_size,4)
        positions[gene,:,4:] = ub*np.random.rand(population_size,3)
        # print(f"Positions : {positions[gene]} dim : {positions[gene,:,:].shape}")
        # print(f"velocities : {velocities[gene]} dim : {positions[gene,:,:].shape}")
        
        new_data = np.zeros((total_timepoints,population_size,num_regulator))
        # print("total timepoints",total_timepoints)
        for tp in range(total_timepoints):
            for pop in range(population_size):
                new_data[tp,pop,:] = data[tp,combination[pop]]
        # print("new data shape", new_data.shape)
        # print("new data",new_data)    
        predicted = np.zeros((49,70))
        
        for tp in range(total_timepoints):
            if tp == 0:
                predicted[tp] = np.tile(data[tp,gene],(1,70))
            else:
                expression = new_data[tp]
                # print("expression Shape : ", expression.shape)
                # print("expression : ", expression)
                expression = np.where(expression != 0, expression, 1)
                # print("positions",positions[gene,:,:].shape)
                # print("positions at one",positions[gene,:,6].shape)
                # print("Position : ", positions[gene,:,:])
                expression = np.power(expression,positions[gene,:,:4])
                # print(f"expression : {expression.shape}")
                # print("expression : ", expression)
                product = np.prod(expression,axis=1)
                # print("Product Shape : ",product.shape)
                # print("Product : ", product)
                # print("predictedtp - 1", predicted[tp-1].shape)
                # print("data[tp-1,gene]", data[tp-1,gene].shape)
                di = np.abs(np.tile(data[tp-1,gene],(1,70)) - predicted[tp-1])
                # print(f"di : {di.shape}")
                # print("di : ", di)
                #print(f"product : {product.shape}")
                predicted[tp] = (delta_t*(positions[gene,:,4]*product))+(1 - delta_t*positions[gene,:,5])*data[tp-1,gene] + positions[gene,:,6]*di
                # print(f"predicted : {predicted[tp].shape}")
                # print("predicted : ", predicted[tp])
        data_transp = np.tile(data[:,gene],(70,1))
        data_transp = np.transpose(data_transp)
        # print("transp_data",data_transp.shape)
        # print("predicted_data",predicted.shape)
        _temp_fitness = np.mean(((predicted - data_transp))**2,axis=0)
        # print(_temp_fitness.shape)
        #print("temp_fitness",_temp_fitness)
        fitness = _temp_fitness.copy()
        # print("fitness",fitness.shape)
        best_fitness = np.min(fitness).copy()
        local_best_position = positions[gene,:,:].copy()
        global_best_position = local_best_position[np.argmin(fitness)].copy()
        # print("local_best_positions",local_best_position)
        # print("fitness",fitness)
        # print("fitness = ",_temp_fitness, "len = ",_temp_fitness.shape)
        # print(f"Fitnesses : {fitness}")
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        print(f"Particle at Index : {np.argmin(fitness)} and particle position : {positions[gene,np.argmin(fitness),:]} fitness : {fitness[np.argmin(fitness)]}")
        c1 = c2 = 2
        for iter in range(Iteration):
            w = np.random.uniform(0,1,(70,1))
            r1 = np.random.uniform(0,1,(70,1))
            r2 = np.random.uniform(0,1,(70,1))
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
                if np.any(np.abs(positions[gene,pop,:]) > ub) or np.any(positions[gene,pop,4:] <= 0):
                            positions[gene,pop,:4] = lb + (ub - lb)*np.random.rand(1,4)
                            positions[gene,pop,4:] = ub*np.random.rand(1,3)
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
                best_combination = combination[np.argmin(fitness)].copy()
                # print(f"******Epoch {iter} : Index : {np.argmin(fitness)} Updated Global Best Fitness : {best_fitness}*******")
                # print(f"******Updated Global Best Position : {global_best_position}*******")
        # #print("fitness = ",_temp_fitness, "len = ",_temp_fitness.shape[1])
        # #print(f"Fitnesses : {fitness}")
            fitness_curve[gene,iter] = best_fitness.copy()
        print(f"Global Best particle : {global_best_position} Index : {np.argmin(fitness)} Best fitness : {best_fitness} fitness at {np.argmin(fitness)} is {np.min(fitness)}")
        print(f"Particle at Index : {np.argmin(fitness)} and particle position : {positions[gene,np.argmin(fitness),:]} fitness : {fitness[np.argmin(fitness)]}")
        print(f"best combination {combination[np.argmin(fitness)]} at Index : {np.argmin(fitness)}")
        all_global_best_position[gene,:] = global_best_position
        for index, combo in enumerate(best_combination):
            result_matrix[gene,combo] = global_best_position[index]
    return result_matrix,all_global_best_position, fitness_curve
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
    Iteration = 4500
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
        csv_filename = "result_matrix_HS_BAPSO"+"_"+str(cur)+"_"+str(Iteration)+"_"+".csv"
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
        image_file_name = "MSE_Curve_HS_BAPSO_"+str(cur)+"_"+str(Iteration)+".png"
        plt.savefig(image_file_name)

#post_processing
for fi in [0.8, 0.9, 1]:
    Result_sets = 10
    All_combined = np.zeros((num_gene,num_gene))
    for result_set in range(Result_sets):
        rs = np.array(pd.read_csv(f'result_matrix_HS_BAPSO_{result_set}_{Iteration}_.csv', sep=','))
        rs = np.where(abs(rs) > 0, 1, 0)
        All_combined += rs
    print("All Combined : ",All_combined)
    G = All_combined/Result_sets
    print("G : ",G)
    G = np.where(G >= fi,1,0)
    actual_result = np.array(pd.read_csv(f'Dataset/actual_data.csv', sep=','))
    print("G : ",G)

    true_positive = int(0)
    false_positive = int(0)
    false_negative = int(0)
    true_negative = int(0)

    for row in range(gene):
        for col in range(gene):
            if G[row][col] == 1 and actual_result[row][col] == 1:
                true_positive += 1
            if G[row][col] == 1 and actual_result[row][col] == 0:
                false_positive += 1
            if G[row][col] == 0 and actual_result[row][col] == 1:
                false_negative += 1
            if G[row][col] == 0 and actual_result[row][col] == 0:
                true_negative += 1

    print(f'True Positive: {true_positive}')
    print(f'False Positive: {false_positive}')
    print(f'False Negative: {false_negative}')
    print(f'True Negative: {true_negative}')

    accuracy = (true_positive + true_negative)/(true_positive + true_negative + false_positive + false_negative)
    f_score = (2*true_positive)/(2*true_positive + false_positive + false_negative)
    TPR = true_positive / (true_positive + false_negative)
    FPR = false_positive / (false_positive + true_negative)
    Specificity = true_negative / (false_positive + true_negative)
    Precision =  true_positive / (true_positive + false_negative)

    print(f'Accuracy: {accuracy}')
    print(f'F-score: {f_score}')
    print(f'TPR: {TPR}')
    print(f'FPR: {FPR}')
    print(f'Specificity: {Specificity}')
    print(f'Precision: {Precision}')

    Results = [
        ['Predicted GRN Matrix ', np.array(G)],
        ['True Positive', true_positive],
        ['False Positive', false_positive],
        ['False Negative', false_negative],
        ['True Negative', true_negative],
        ['Accuracy', accuracy],
        ['F-score', f_score],
        ['TPR', TPR],
        ['FPR', FPR],
        ['Specificity', Specificity],
        ['Precision', Precision]
    ]

    filename = 'Result_set_of_BAPSO_HS_fi_is_'+str(fi)+'_Iteration_'+str(Iteration)+'_Gene_'+str(num_gene)+'_.csv'
    pd.DataFrame(Results).to_csv(filename, index=False)


