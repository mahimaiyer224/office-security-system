import face_recognition
import os
from pymongo import MongoClient
import pickle

def generate_encoding(image_path):
    try:
        # Load Image
        image = face_recognition.load_image_file(image_path)
        
        # Detect faces with a specific model
        face_locations = face_recognition.face_locations(image, model="hog")  # 'hog' or 'cnn'
        
        if not face_locations:
            print(f"No face found in {image_path}")
            return None
        
        # Generate Face Encoding
        encoding = face_recognition.face_encodings(image, face_locations)[0]
        return encoding

    except Exception as e:
        print(f"Error processing {image_path}: {e}")
        return None

# Connect to MongoDB and fetch image data
client = MongoClient('mongodb://localhost:27017/')
db = client['office_security_system']
collection = db['images']
image_data = collection.find()

known_face_encodings = {}

for data in image_data:
    character_name = data['name']  # Or use a relevant field that identifies the character
    image_path = data['image']

    # Generate Face Encoding
    encoding = generate_encoding(image_path)
    
    if encoding is not None:
        # Store Encoding
        known_face_encodings[character_name] = encoding

# Save Encodings to a File
with open('face_encodings.pkl', 'wb') as f:
    pickle.dump(known_face_encodings, f)

print("Face encodings have been saved successfully!")
