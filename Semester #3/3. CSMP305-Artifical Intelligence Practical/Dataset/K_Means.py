from matplotlib import cm
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

centroids = []
    
def calculate_euclidean_distance(point1, point2):
    distance = np.linalg.norm(point1-point2)
    return distance

def choose_new_centroid(clusters,data,no_of_centroid):
    centroids.clear()
    for i in range(no_of_centroid):
        sum = np.zeros(data.shape[1]-1)
        for j in range(data.shape[0]):
            if data[j,:1] in clusters[i]:
                sum += data[j,1:]
        print(f"New centoid {i} is : {sum/len(clusters[i])}")
        centroids.append(np.array(sum/len(clusters[i])))
            
    
def k_means(data, no_of_centroid):
    centroids.clear()
    clusters = {i: [] for i in range(no_of_centroid)}
    for i in range(no_of_centroid):
        random_index = np.random.choice(data.shape[0])
        centroid = data[random_index, 1:]
        centroids.append(centroid)
        print("centroid :", centroid)
    changed = True
    iter = 0
    while(changed):
        print(f"Epoch:{iter}")
        iter+=1
        changed = False
        for index in range(data.shape[0]):
            distances = np.zeros(no_of_centroid)
            for i, centroid in enumerate(centroids):
                distance = calculate_euclidean_distance(data[index,1:], centroid)
                distances[i] = distance
                # print(f"distance from centroid {i} is : {distances[i]}")
            # print(f"Minimum distance : {min(distances)}")
            for i in range(no_of_centroid):
                if distances[i] == min(distances) and data[index,0] in clusters[i]:
                   pass
                elif distances[i] == min(distances) and data[index,0] not in clusters[i]:
                    clusters[i].append(data[index,0])
                    changed = True
                elif distances[i] != min(distances) and data[index,0] in clusters[i]:
                    clusters[i].remove(data[index,0])
        for i,cluster in enumerate(clusters):
            print(f"cluster : {i}",clusters[i],f"Size : {len(clusters[i])}")
        choose_new_centroid(clusters,data,no_of_centroid)  
    return clusters        

if __name__ == '__main__':
    # Load the data
    data = pd.read_csv('Iris.csv').to_numpy()
    # data = np.array([
    #     [1, 1.0, 1.0],
    #     [2, 2.0, 3.0],
    #     [3, 0.5, 4.0],
    #     [4, 1.0, 5.4]
    # ])
    # print(data)
    WCSS = []
    WCSS.append(float('inf'))
    threshold = 50
    final_k = None
    for k in range(1,10):
        clusters = k_means(data, k)
        print(clusters)
        sum = int(0)
        temp = []
        for i, centroid in enumerate(centroids):
            for j in range(data.shape[0]):
                if data[j,:1] in clusters[i]:
                    sum+=calculate_euclidean_distance(np.array(centroid),np.array(data[j,1:]))**2
            temp.append(sum)
        if(np.min(WCSS) > np.sum(temp)/len(temp)):
            if(np.abs(np.min(WCSS) - np.sum(temp)/len(temp)) > threshold):
                final_k = k
        WCSS.append(np.sum(temp)/len(temp))
    plt.plot(range(0,10), WCSS, marker= 'x')
    plt.xlabel("Values of K")  
    plt.ylabel("WCSS")
    plt.title("Finding Elbow")
    plt.show()
                              
    clusters = k_means(data,final_k)
    plt.figure(figsize=(10,10))
    for i, centroid in enumerate(centroids):
        plt.scatter(centroid[0],centroid[1],marker='x',color = 'red')
        for j in range(data.shape[0]):
            if data[j,:1] in clusters[i]:
                plt.scatter(data[j,1],data[j,2], c=f'C{i}') 
    plt.xlabel("SepalLengthCm")  
    plt.ylabel("SepalWidthCm")
    plt.title("K_Means of Iris Dataset")
    plt.show()
    
    
    