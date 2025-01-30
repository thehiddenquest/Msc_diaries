import pandas as pd
import graphviz

# Load the dataset
golf_play = pd.read_csv('GolfPlay.csv')

# Function to calculate Gini index for each feature
def calculate_gini_index(data, target_column, features):
    gini_index_array = []
    for column in features:  # Only loop over the features passed in
        column_array = data[column].unique()
        gini_index = 0
        for value in column_array:
            subset = data[data[column] == value]
            total = len(subset)
            if total == 0:
                continue
            yes = len(subset[subset[target_column] == 'Yes'])
            no = len(subset[subset[target_column] == 'No'])
            squared_fraction_1 = (yes / total) ** 2
            squared_fraction_2 = (no / total) ** 2
            squared_fraction = 1 - squared_fraction_1 - squared_fraction_2
            gini_index += squared_fraction * (total / len(data))
        gini_index_array.append(gini_index)
    return gini_index_array

# Function to build the decision tree
def build_decision_tree(data, target_column, features):
    print(f"Building tree for features: {features}")  # Debugging print
    
    # Base case 1: If the target column is pure (only one class)
    if len(data[target_column].unique()) == 1:
        return data[target_column].iloc[0]

    # Base case 2: If there are no more features to split on, return the majority class
    if len(features) == 0:
        print("No more features to split on. Returning majority class.")
        return data[target_column].value_counts().idxmax()

    # Calculate Gini indices for each feature
    gini_indices = calculate_gini_index(data, target_column, features)

    # Debugging: Check if gini_indices are being calculated correctly
    print(f"Gini indices for features: {gini_indices}")

    # If no valid gini indices are found, return the majority class
    if not gini_indices:
        print("No valid Gini indices found. Returning majority class.")
        return data[target_column].value_counts().idxmax()
    
    # Find the best feature (the one with the minimum Gini index)
    best_feature_index = gini_indices.index(min(gini_indices))

    # Ensure that the best_feature_index is valid and does not exceed the length of features
    if best_feature_index >= len(features):
        print(f"Error: best_feature_index {best_feature_index} is out of range for the features list.")
        return data[target_column].value_counts().idxmax()
    
    # Get the best feature and create the subtree
    best_feature = features[best_feature_index]
    tree = {best_feature: {}}

    # Remove the best feature from the list of features for further splitting
    features = [f for f in features if f != best_feature]
    
    # Recurse on each subset of the data where the feature has a particular value
    for value in data[best_feature].unique():
        subset = data[data[best_feature] == value]
        subtree = build_decision_tree(subset, target_column, features)
        tree[best_feature][value] = subtree

    return tree

# Function to visualize the decision tree using graphviz
def visualize_tree(tree, parent_name="root"):
    dot = graphviz.Digraph(format='png', engine='dot')

    def add_nodes_edges(tree, parent_name):
        for feature, branches in tree.items():
            node_name = f"{parent_name}_{feature}"
            dot.node(node_name, feature)
            dot.edge(parent_name, node_name)
            for value, subtree in branches.items():
                # Handling subtrees: if it's a dictionary, recurse; if it's a leaf, label it as such
                if isinstance(subtree, dict):
                    add_nodes_edges(subtree, node_name)
                else:
                    leaf_name = f"{node_name}_{value}"
                    dot.node(leaf_name, str(subtree))  # Leaf is the classification ("Yes"/"No")
                    dot.edge(node_name, leaf_name)

    add_nodes_edges(tree, parent_name)
    return dot

# Ensure 'GolfPlay.csv' is available and contains the necessary columns.
# Build the decision tree using the golf_play dataset
tree = build_decision_tree(golf_play, 'Decision', list(golf_play.columns[:-1]))

# Visualize the decision tree and save it as a PNG image
dot = visualize_tree(tree)
dot.render('decision_tree', view=True)

# Optionally, print the tree structure
print(tree)