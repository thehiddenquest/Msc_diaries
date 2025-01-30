import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from random import choice

def calculate_cost(dataset, medoids, clusters):
    """
    Calculate the total cost (sum of distances of points from their medoids).
    """
    total_cost = 0
    for medoid, points in clusters.items():
        for point in points:
            total_cost += np.linalg.norm(dataset[point] - dataset[medoid])
    return total_cost

def k_medoids(dataset, no_of_medoids, max_iter=100):
    """
    K-Medoids clustering algorithm.
    :param dataset: NumPy array of shape (n_samples, n_features)
    :param no_of_medoids: Number of medoids to select
    :param max_iter: Maximum iterations
    :return: Medoids, clusters, and final cost
    """
    if isinstance(dataset, pd.DataFrame):
        dataset = dataset.to_numpy()

    n_samples = dataset.shape[0]

    # Randomly initialize medoids
    medoids = np.random.choice(n_samples, no_of_medoids, replace=False)
    if isinstance(medoids, list):
        medoids = np.array(medoids)

    clusters = {medoid: [] for medoid in medoids}
    for _ in range(max_iter):
        # Assign points to the nearest medoid
        clusters = {medoid: [] for medoid in medoids}
        for i, point in enumerate(dataset):
            closest_medoid = min(medoids, key=lambda m: np.linalg.norm(point - dataset[m]))
            clusters[closest_medoid].append(i)

        # Update medoids by minimizing the total cost for each cluster
        new_medoids = np.array(medoids)
        for medoid in medoids:
            cluster_points = clusters[medoid]
            if len(cluster_points) == 0:
                continue
            costs = []
            for candidate in cluster_points:
                cost = sum(np.linalg.norm(dataset[candidate] - dataset[point]) for point in cluster_points)
                costs.append((candidate, cost))
            best_candidate, best_cost = min(costs, key=lambda x: x[1])
            new_medoids[np.where(medoids == medoid)[0][0]] = best_candidate

        # Check for convergence (if medoids do not change)
        if np.array_equal(new_medoids, medoids):
            break
        medoids = new_medoids

    # Calculate the total cost
    total_cost = calculate_cost(dataset, medoids, clusters)
    return medoids, clusters, total_cost

if __name__ == '__main__':
    # Generate a simple dataset for demonstration
    np.random.seed(42)
    csv_file = pd.read_csv('Dataset/kmeans_test.csv')
    dataset = csv_file.iloc[:,1:-1]  

    # Number of clusters
    no_of_medoids = 3

    # Apply K-Medoids
    medoids, clusters, cost = k_medoids(dataset, no_of_medoids)

    print(f"Final Medoids: {medoids}")
    print(f"Final Cost: {cost}")

    # Plot the results
    plt.figure(figsize=(8, 6))
    colors = ['r', 'g', 'b']
    for medoid, cluster_points in clusters.items():
        cluster_data = dataset[cluster_points]
        plt.scatter(cluster_data[:, 0], cluster_data[:, 1], label=f'Cluster {medoid}', alpha=0.6)
        plt.scatter(dataset[medoid, 0], dataset[medoid, 1], c='k', marker='x', s=200, label=f'Medoid {medoid}')
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title('K-Medoids Clustering')
    plt.legend()
    plt.show()