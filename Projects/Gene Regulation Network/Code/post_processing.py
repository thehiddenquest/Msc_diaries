import pandas as pd
import numpy as np

data = pd.read_csv("result_matrix_1000_0.csv")

data_array = data.to_numpy()

modified_array = np.where(abs(data_array) < 0.8, 0, 1)

# Display the modified array
print(modified_array)
