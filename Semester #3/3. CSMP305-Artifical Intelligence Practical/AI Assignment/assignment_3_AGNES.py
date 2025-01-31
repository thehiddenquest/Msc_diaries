import numpy as np
import pandas as pd
from sklearn import datasets
from sklearn.preprocessing import StandardScaler, MinMaxScaler
from sklearn.cluster import AgglomerativeClustering
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
from scipy.cluster.hierarchy import linkage, dendrogram

# Load the Iris dataset
iris = datasets.load_iris()
X = iris.data  # Features (Sepal Length, Sepal Width, Petal Length, Petal Width)
y = iris.target  # True labels 

# Convert to DataFrame for better preprocessing
df = pd.DataFrame(X, columns=iris.feature_names)

# Handle missing values (if any) by filling with mean values
df.fillna(df.mean(), inplace=True)

# Remove duplicate rows (if any)
df.drop_duplicates(inplace=True)

# Standardize the features
scaler = StandardScaler()
X_scaled = scaler.fit_transform(df)

# Define a function to apply AGNES with different linkage methods
def agnes_clustering(linkage_method):
    agnes = AgglomerativeClustering(n_clusters=3, linkage=linkage_method)
    cluster_labels = agnes.fit_predict(X_scaled)

    # Compute linkage matrix for dendrogram
    Z = linkage(X_scaled, method=linkage_method)

    # Reduce dimensionality for visualization using PCA
    pca = PCA(n_components=2)
    X_pca = pca.fit_transform(X_scaled)

    # Plot the clusters
    plt.figure(figsize=(8, 6))
    for cluster in range(3):
        plt.scatter(
            X_pca[np.where(cluster_labels == cluster), 0],
            X_pca[np.where(cluster_labels == cluster), 1],
            label=f"Cluster {cluster + 1}"
        )

    plt.title(f"AGNES Clustering ({linkage_method.capitalize()} Linkage)")
    plt.xlabel("Principal Component 1")
    plt.ylabel("Principal Component 2")
    plt.legend()
    plt.show()

    # Plot the dendrogram
    plt.figure(figsize=(10, 6))
    dendrogram(Z)
    plt.title(f"Dendrogram ({linkage_method.capitalize()} Linkage)")
    plt.xlabel("Data Points")
    plt.ylabel("Distance")
    plt.show()

# Apply AGNES with single linkage
agnes_clustering("single")

# Apply AGNES with complete linkage
agnes_clustering("complete")

# Apply AGNES with average linkage
agnes_clustering("average")
