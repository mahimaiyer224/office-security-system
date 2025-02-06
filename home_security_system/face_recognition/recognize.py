import face_recognition
import pickle
import sys
import os

if len(sys.argv) != 2:
    print("Usage: python recognize.py <image_path>")
    sys.exit(1)

image_path = sys.argv[1]  # Get image path from command line argument

# Check if the file exists
if not os.path.isfile(image_path):
    print(f"Error: File {image_path} does not exist.")
    sys.exit(1)

with open('face_encodings.pkl', 'rb') as f:
    known_face_encodings = pickle.load(f)

def recognize(image_path):
    image = face_recognition.load_image_file(image_path)
    face_locations = face_recognition.face_locations(image)
    face_encodings = face_recognition.face_encodings(image, face_locations)

    recognized_faces = []
    for face_encoding in face_encodings:
        matches = face_recognition.compare_faces(list(known_face_encodings.values()), face_encoding)
        name = "Unknown"
        if True in matches:
            first_match_index = matches.index(True)
            name = list(known_face_encodings.keys())[first_match_index]

        recognized_faces.append(name)

    return recognized_faces

recognized_faces = recognize(image_path)
print("Recognized face is of", recognized_faces)
