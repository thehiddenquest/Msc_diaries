import numpy as np
import pandas as pd
from scipy.cluster.hierarchy import dendrogram, linkage
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler

# Load the Iris dataset
df = pd.read_csv('Iris.csv')

# Drop the 'Id' and 'Species' columns as they are not part of the feature set
X = df.drop(columns=['Id', 'Species'])

# Handle missing values by filling with mean values
X.fillna(X.mean(), inplace=True)

# Remove duplicate rows
X.drop_duplicates(inplace=True)

# Standardize the features
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Linkage methods to evaluate
linkage_methods = ['single', 'complete', 'average']

# Plot dendrograms for each linkage method
plt.figure(figsize=(15, 5))
for i, method in enumerate(linkage_methods, 1):
    # Perform hierarchical clustering
    Z = linkage(X_scaled, method)

    # Plot dendrogram
    plt.subplot(1, 3, i)
    dendrogram(Z)
    plt.title(f'Hierarchical Clustering Dendrogram ({method.capitalize()} Linkage)')
    plt.xlabel('Data Point Index')
    plt.ylabel('Distance')

plt.tight_layout()
plt.show()
