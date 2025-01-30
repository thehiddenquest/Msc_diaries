import numpy as np

given_graph = np.array([['inf', 43, 0, 44, 46],
               [43, 'inf', 39, 45, 35],
               [0, 39, 'inf', 42, 48],
               [44, 45, 42, 'inf', 23],
               [46, 35, 48, 23, 'inf']])

def objective_function(X,num_node,max_bit):
    temp = np.zeros(num_node)
    i = int(0)
    j = int(0)
    reward = int(0)
    while(i < num_node*max_bit):
        decimal_num = str(X[i:i+max_bit])
        decimal_num = decimal_num.replace('[', '').replace(']', '').replace(' ', '')
        temp[j] = int(decimal_num,2)
        j += 1
        i += max_bit
    visited = np.zeros(num_node)
    visited[0] = (temp[0] if temp[0] < num_node else 0)
    for i in range(1,num_node):
        if temp[i] < num_node and np.all(visited != temp[i]):
            visited[i] = temp[i]
        else:
            while(True):
                if visited[i-1]+1 != num_node and np.all(visited != visited[i-1]+1):
                    visited[i] = visited[i-1] + 1
                    break
                else:
                    for j in range(num_node):
                        if np.all(visited != j):
                            visited[i] = j
                            break
                    break
                
    for i in range(num_node-1):
        reward += int(given_graph[int(visited[i]), int(visited[i+1])])
    reward += int(given_graph[int(visited[-1]),int(visited[0])])
    print("Tour Path : ",visited)
    print("Reward : ",reward)
    return reward, visited

def cross_over(X,pop_size,dim):

    index1, index2 = np.random.randint(0,pop_size,2)
    x1 = X[index1,:]
    x2 = X[index2,:]
    print("........Before Cross Over..........")
    print(x1)
    print(x2)
    cross_over_point = np.random.randint(0,dim)
    for i in range(cross_over_point,dim):
        temp = x1[i].copy()
        x1[i] = x2[i].copy()
        x2[i] = temp.copy()
            
    print("........After Cross Over..........")
    print(x1)
    print(x2)

    return x1,x2,index1,index2
def GA(pop_size,max_iter,dim,num_node,max_bit):
    bestX = None
    fitness = 0
    best_tour_path = None
    X = np.random.randint(0,2,(pop_size,dim))
    print(" Initial Population : ",X)
    for iter in range(max_iter):
        x1, x2,index1,index2 = cross_over(X,pop_size,dim)
        X[index1,:] = x1
        X[index2,:] = x2
        temp_fitness,tour_path = objective_function(X[index1,:],num_node,max_bit)
        if fitness < temp_fitness:
            fitness = temp_fitness
            bestX = X[index1]
            best_tour_path = tour_path
            print("Updated Tour path : ",best_tour_path)
            print("Updated Fitness : ",fitness)
        temp_fitness,tour_path = objective_function(X[index2,:],num_node,max_bit)
        if fitness < temp_fitness:
            fitness = temp_fitness
            bestX = X[index2]
            best_tour_path = tour_path
            print("Updated tour path : ",best_tour_path)
            print("Updated Fitness : ",fitness)
    return best_tour_path,fitness
if __name__ == '__main__':
    num_node = 5
    max_bit = 3
    bestX,fitness = GA(10,100,num_node*max_bit,num_node,max_bit)
    print("Best Solution : ",bestX)
    print("Fitness : ",fitness)