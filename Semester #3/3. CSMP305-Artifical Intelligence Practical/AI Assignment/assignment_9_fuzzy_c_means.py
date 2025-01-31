import numpy as np
from sklearn.datasets import fetch_california_housing
from sklearn.preprocessing import StandardScaler
import skfuzzy as fuzz
import matplotlib.pyplot as plt

# Load California Housing Dataset
data = fetch_california_housing()
X = data.data  # Features

# Normalize the data for better clustering performance
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Define the number of clusters
n_clusters = 3

# Apply Fuzzy C-Means algorithm
cntr, u, u0, d, jm, p, fpc = fuzz.cluster.cmeans(
    X_scaled.T,  # Transpose to shape (features, samples)
    c=n_clusters,  # Number of clusters
    m=2.0,         # Fuzziness parameter
    error=0.005,   # Stopping criterion
    maxiter=1000,  # Maximum number of iterations
    init=None      # Initial guess for membership values
)

# Assign each data point to the cluster with the highest membership value
cluster_membership = np.argmax(u, axis=0)

# Visualize the clustering result (using first two features for simplicity)
plt.figure(figsize=(8, 6))
colors = ['r', 'g', 'b']
for i in range(n_clusters)
    plt.scatter(
        X_scaled[cluster_membership == i, 0],
        X_scaled[cluster_membership == i, 1],
        label=f'Cluster {i+1}',
        color=colors[i],
        alpha=0.6
    )
plt.title('Fuzzy C-Means Clustering (California Housing Dataset)')
plt.xlabel('Feature 1 (Standardized)')
plt.ylabel('Feature 2 (Standardized)')
plt.legend()
plt.grid()
plt.show()

# Print final cluster centers
print(Cluster Centers)
print(cntr)
