import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelEncoder

# Dataset
data = {
    'Age': ['youth', 'youth', 'youth', 'middle', 'senior', 'senior', 'middle', 'youth', 'middle', 'senior'],
    'Income': ['low', 'high', 'high', 'medium', 'low', 'low', 'high', 'medium', 'low', 'medium'],
    'Student': ['no', 'yes', 'no', 'yes', 'yes', 'yes', 'yes', 'no', 'no', 'yes'],
    'Credit_Rating': ['fair', 'excellent', 'fair', 'fair', 'fair', 'excellent', 'excellent', 'fair', 'excellent', 'fair'],
    'Buy_Computer': ['yes', 'no', 'yes', 'yes', 'yes', 'yes', 'yes', 'no', 'yes', 'yes']
}

# Create DataFrame
df = pd.DataFrame(data)

# Initialize LabelEncoder for each column separately
le_age = LabelEncoder()
le_income = LabelEncoder()
le_student = LabelEncoder()
le_credit_rating = LabelEncoder()
le_buy_computer = LabelEncoder()

# Fit the LabelEncoder on each column of the training data
df['Age'] = le_age.fit_transform(df['Age'])
df['Income'] = le_income.fit_transform(df['Income'])
df['Student'] = le_student.fit_transform(df['Student'])
df['Credit_Rating'] = le_credit_rating.fit_transform(df['Credit_Rating'])
df['Buy_Computer'] = le_buy_computer.fit_transform(df['Buy_Computer'])

# Features and target
X = df[['Age', 'Income', 'Student', 'Credit_Rating']]
y = df['Buy_Computer']

# Split the data into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

# Train a Naïve Bayes classifier
nb = GaussianNB()
nb.fit(X_train, y_train)

# Predict class labels for test samples
y_pred = nb.predict(X_test)

# Predict for unknown samples
unknown_samples = pd.DataFrame({
    'Age': ['senior'],
    'Income': ['low'],
    'Student': ['yes'],
    'Credit_Rating': ['excellent']
})

print(f"Inserted data\n", unknown_samples)

# Use the same LabelEncoder transformations to the unknown samples
unknown_samples['Age'] = le_age.transform(unknown_samples['Age'])
unknown_samples['Income'] = le_income.transform(unknown_samples['Income'])
unknown_samples['Student'] = le_student.transform(unknown_samples['Student'])
unknown_samples['Credit_Rating'] = le_credit_rating.transform(unknown_samples['Credit_Rating'])

# Make predictions using the Naïve Bayes classifier
predicted_labels = nb.predict(unknown_samples[['Age', 'Income', 'Student', 'Credit_Rating']])
print("Predicted class labels for unknown samples:", le_buy_computer.inverse_transform(predicted_labels))
