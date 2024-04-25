
import tkinter as tk
import turtle
import random

class ProposalWindow(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("A Special Message")
        self.geometry("400x300")  # Adjust window size as desired

        self.message_label = tk.Label(self, text="Thinking of you...", font=("Arial", 20))
        self.message_label.pack(pady=20)

        self.start_button = tk.Button(self, text="Start", command=self.start_animation, font=("Arial", 16))
        self.start_button.pack(pady=20)

    def start_animation(self):
        self.start_button.config(state="disabled")  # Disable button while animation plays
        self.message_label.config(text="Get ready...")

        # Create a separate Tkinter window for animation (prevents blocking)
        animation_window = tk.Tk()
        animation_window.title("Will You?")
        animation_window.withdraw()  # Hide window decorations

        # Create Turtle screen and set background
        screen = turtle.Screen()
        screen.setup(width=600, height=400)  # Adjust screen size as desired
        screen.bgcolor("black")
        screen.tracer(0)  # Turn off automatic animation

        # Create pen and set properties
        pen = turtle.Turtle()
        pen.speed(0)  # Set animation speed (0 for fastest)
        pen.pensize(3)
        pen.hideturtle()

        colors = ["red", "orange", "yellow", "green", "lightblue", "blue", "purple", "pink"]

        # Draw hearts with random colors
        for i in range(100):
            pen.penup()
            x = random.randint(-250, 250)
            y = random.randint(-150, 150)
            pen.goto(x, y)
            pen.pendown()
            pen.color(random.choice(colors))
            pen.begin_fill()
            pen.left(140)
            pen.forward(111.65)
            pen.circle(90, 225)
            pen.left(120)
            pen.circle(90, 225)
            pen.forward(111.65)
            pen.end_fill()
            screen.update()  # Update screen after each heart

        # Write proposal message
        pen.penup()
        pen.goto(0, 0)
        pen.color("white")
        pen.write("Will you be my...", align="center", font=("Arial", 30, "bold"))
        pen.goto(0, -50)
        pen.write("Valentine?", align="center", font=("Arial", 30, "bold"))

        # Allow user to close animation window
        animation_window.protocol("WM_DELETE_WINDOW", self.destroy_animation)
        screen.listen()  # Listen for key presses, etc.

        # Start the animation loop (moved here)
        screen.mainloop()

    def destroy_animation(self):
        turtle.bye()  # Close Turtle screen
        self.destroy()  # Close the proposal window

if __name__ == "__main__":
    proposal_window = ProposalWindow()
    proposal_window.mainloop()