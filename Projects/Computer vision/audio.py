import socket
import pyaudio
import threading
import time

# Audio settings
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100

# IP and port settings
PC_IP = "0.0.0.0"  # Listen on all available interfaces
PC_PORT = 5000
PHONE_IP = "192.168.0.101"  # Replace with your phone's IP
PHONE_PORT = 8125

# Initialize PyAudio
p = pyaudio.PyAudio()

def start_audio_stream():
    # Start audio streams
    output_stream = p.open(format=FORMAT, channels=CHANNELS, rate=RATE, output=True, frames_per_buffer=CHUNK)
    input_stream = p.open(format=FORMAT, channels=CHANNELS, rate=RATE, input=True, frames_per_buffer=CHUNK)

    # Set up TCP sockets
    pc_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    pc_socket.bind((PC_IP, PC_PORT))
    pc_socket.listen(1)  # Allow one connection (phone)

    # Accept connection from the phone
    print("Waiting for phone to connect...")
    conn, _ = pc_socket.accept()
    print("Connected to phone.")

    def receive_from_phone():
        while True:
            try:
                data = conn.recv(CHUNK * 2)
                if not data:
                    break
                output_stream.write(data)
            except Exception as e:
                print(f"Error receiving audio: {e}")
                break

    def send_to_phone():
        while True:
            try:
                data = input_stream.read(CHUNK, exception_on_overflow=False)
                conn.sendall(data)
            except Exception as e:
                print(f"Error sending audio: {e}")
                break

    # Start threads for bidirectional audio streaming
    receive_thread = threading.Thread(target=receive_from_phone, daemon=True)
    send_thread = threading.Thread(target=send_to_phone, daemon=True)

    receive_thread.start()
    send_thread.start()

    # Keep main thread alive while streaming
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        print("Streaming stopped.")

    # # Close streams
    # output_stream.stop_stream()
    # output_stream.close()
    # input_stream.stop_stream()
    # input_stream.close()
    # conn.close()
    # pc_socket.close()

# Run the audio stream function
start_audio_stream()