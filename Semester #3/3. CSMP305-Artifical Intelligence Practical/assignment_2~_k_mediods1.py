from sklearn_extra.cluster import KMedoids
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

dataset_name = 'Iris.csv'
# Read Input data
csv_file  =  pd.read_csv(f'Dataset/{dataset_name}')                                                                         # Read Dataset
X = csv_file.iloc[:,1:-1]   

# Function to calculate inertia for different numbers of clusters
def calculate_inertia(X, max_clusters=10):
    inertias = []
    for n_clusters in range(1, max_clusters + 1):
        kmedoids = KMedoids(n_clusters=n_clusters, random_state=0)
        kmedoids.fit(X)
        # Inertia: Sum of distances of samples to their closest medoid
        inertia = np.sum(np.min(kmedoids.transform(X), axis=1))
        inertias.append(inertia)
    return inertias

# Calculate inertia for 1 to 10 clusters
max_clusters = 10
inertias = calculate_inertia(X, max_clusters=max_clusters)

# Plot the elbow curve
plt.figure(figsize=(8, 6))
plt.plot(range(1, max_clusters + 1), inertias, marker='o', linestyle='--')
plt.title("Elbow Method for Optimal Clusters (K-Medoids)")
plt.xlabel("Number of Clusters")
plt.ylabel("Inertia (Sum of Distances to Medoids)")
plt.xticks(range(1, max_clusters + 1))
plt.grid()
plt.show()

# K-Medoids clustering for the optimal number of clusters (manually set after elbow point)
optimal_clusters = 4  # Set this based on the elbow point
kmedoids = KMedoids(n_clusters=optimal_clusters, random_state=0)
kmedoids.fit(X)

# Visualize the clustering results
labels = kmedoids.labels_
medoids = kmedoids.medoid_indices_
