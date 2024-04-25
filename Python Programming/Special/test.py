import tkinter as tk

# Create the main window
root = tk.Tk()
root.title("Simple Tkinter Program")

# Create a label widget
label = tk.Label(root, text="Hello, Tkinter!")
label.pack(pady=10, padx=10)  # Add some padding around the label

# Run the Tkinter event loop
root.mainloop()