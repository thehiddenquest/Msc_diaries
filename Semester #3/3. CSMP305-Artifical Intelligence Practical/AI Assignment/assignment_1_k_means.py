import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def print_group(clusters):
        for cluster_id, cluster_points in clusters.items():
                print(f"\nCluster {cluster_id} ({len(cluster_points)} points):")
                for index, point in cluster_points:
                        print(f"  Index: {index}, Data Point: {point}")

def calculate_wcss(dataset, centroids, clusters):
        wcss = 0
        for cluster_id, cluster_points in clusters.items():
                centroid = centroids[cluster_id]
                for index, point in cluster_points:
                # Use the data point directly for WCSS calculation
                        wcss += np.sum((point - centroid) ** 2)
        return wcss

def  kmeans(dataset,no_of_centroid,test = 0):
    # Ensure dataset is a NumPy array for efficient computation
        if isinstance(dataset, pd.DataFrame):
                dataset = dataset.to_numpy()

    # Choosing initial centroids
        random_indices = np.random.randint(0,len(dataset),no_of_centroid)
        centroids = dataset[random_indices,:]
        if test ==0:                                                                                  # If this is not test case then print
                print(f"Initial centroids are : {centroids}")

    # K means algorithm
        clusters = {i: [] for i in range(no_of_centroid)}                                 # Creating clusters / groups
        tolerance = 1e-2                           # Convergence tolerance (distance change between centroids)
        shifted = True                                                        # Variable to track either centroids are shifted
        iter = 0
        while shifted:
                iter = iter + 1
                shifted = False
                distances = np.sqrt(np.sum((dataset[:, np.newaxis, :] - centroids[np.newaxis, :, :])**2, axis=2))                                                                          # If this is not test case then print
                labels = np.argmin(distances, axis=1)                       # Assign points to the nearest centroid
                clusters = {i: [] for i in range(no_of_centroid)}                        # Creating clusters / groups

                for i, label in enumerate(labels):
                        clusters[label].append((i, dataset[i]))  # Append (index, data point) to the cluster
                if test == 0:
                        print_group(clusters)

                new_centroids = np.array([np.mean([point[1] for point in clusters[i]], axis=0) if len(clusters[i]) > 0 else centroids[i] for i in range(no_of_centroid)])
                if test ==0:
                        print(f"New centroids are : {new_centroids}")
                centroid_shift = np.linalg.norm(new_centroids - centroids)  # Calculate the Euclidean distance between centroids

                # If the centroid shift is smaller than the tolerance, stop
                if centroid_shift >= tolerance:
                        centroids = new_centroids
                        shifted = True
                else:
                        if test ==0:
                                print(f"Convergence reached after {iter} iterations.")

        wcss = calculate_wcss(dataset, centroids, clusters)
        return centroids, clusters, wcss

if __name__ == '__main__':
        np.random.seed(42)

        dataset_name = 'Iris.csv'
    # Read Input data
        csv_file  =  pd.read_csv(f'Dataset/{dataset_name}')                                                                         # Read Dataset
        dataset = csv_file.iloc[:,1:-1]                                     # Exclusion of "ID" and "Species" column

        # csv_file = pd.read_csv('Dataset/kmeans_test.csv')
        # dataset = csv_file.iloc[:, 1:]

    # # K means Clustering
    #     no_of_centroid = 2
    #     k_means_centroids,k_means_clusters,wcss = kmeans(dataset,no_of_centroid,test = 0)
    # K-means Clustering
        max_clusters = 10
        wcss_values = []
        for no_of_centroid in range(1, max_clusters + 1):
            centroids, clusters, wcss = kmeans(dataset, no_of_centroid,test = 1)
            wcss_values.append(wcss)

        # Plot WCSS to find the "elbow point"
        plt.plot(range(1, max_clusters + 1), wcss_values, marker='o')
        plt.xlabel('Number of Clusters')
        plt.ylabel('WCSS')
        plt.title('Elbow Method for Optimal K')
        plt.savefig(f"Elbow Method for Optimal K {dataset_name}.png")
        plt.show()
       
        
        no_of_centroid = 2
        print(f"After Elbow Method, the optimal number of centroid is {no_of_centroid}");
        centroids, clusters, wcss = kmeans(dataset, no_of_centroid,test = 0)



