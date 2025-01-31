import numpy as np
import pandas as pd
import math
from collections import Counter
import graphviz

# Load dataset
df_data = pd.read_csv('GolfPlay.csv')

# Handle missing values by filling with the mode of each column
df_data.fillna(df_data.mode().iloc[0], inplace=True)

# Remove duplicate rows
df_data.drop_duplicates(inplace=True)

# Convert categorical data into numerical format
df = df_data.apply(lambda x: x.astype('category').cat.codes if x.dtype == 'object' else x)

# Entropy Calculation
def entropy(y):
    counter = Counter(y)
    total = len(y)
    return -sum((count/total) * math.log2(count/total) for count in counter.values())

# Information Gain Calculation
def information_gain(df, feature, target):
    total_entropy = entropy(df[target])
    values = df[feature].unique()
    weighted_entropy = sum((len(df[df[feature] == v]) / len(df)) * entropy(df[df[feature] == v][target]) for v in values)
    return total_entropy - weighted_entropy

# ID3 Algorithm - Recursively Build the Decision Tree
def id3(df, features, target, tree=None):
    if len(df[target].unique()) == 1:
        return df[target].iloc[0]
    if len(features) == 0:
        return df[target].mode()[0]

    best_feature = max(features, key=lambda f: information_gain(df, f, target))
    tree = {best_feature: {}}

    for value in df[best_feature].unique():
        subset = df[df[best_feature] == value]
        tree[best_feature][value] = id3(subset, [f for f in features if f != best_feature], target)

    return tree

# Build the decision tree
features = df.columns[:-1]
target = 'Decision'
decision_tree = id3(df, features, target)

# Print Decision Tree
import pprint
pprint.pprint(decision_tree)

# Function to visualize the tree
def visualize_tree(tree, parent=None, graph=None):
    if graph is None:
        graph = graphviz.Digraph(format="png")

    if isinstance(tree, dict):
        for node, sub_tree in tree.items():
            if parent is None:
                graph.node(node, label=node, shape="diamond")
            else:
                graph.node(node, label=node, shape="diamond")
                graph.edge(parent, node)

            for value, branch in sub_tree.items():
                child_name = f"{node}_{value}"
                graph.node(child_name, label=str(value))
                graph.edge(node, child_name)
                visualize_tree(branch, parent=child_name, graph=graph)
    else:
        graph.node(str(tree), label=str(tree), shape="box")
        graph.edge(parent, str(tree))

    return graph

# Draw the decision tree
graph = visualize_tree(decision_tree)
graph.render("decision_tree", view=True)
