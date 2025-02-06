from PIL import Image

def resize_image(image_path, output_path, size=(800, 800)):
    with Image.open(image_path) as img:
        img = img.resize(size)
        img.save(output_path)

# Resize your problematic images
resize_image('C:\\Users\\mahih\\Desktop\\IRP\\Images\\ps.jpg', 'C:\\Users\\mahih\\Desktop\\IRP\\Images\\ps_resized.jpg')
resize_image('C:\\Users\\mahih\\Desktop\\IRP\\Images\\om.jpg', 'C:\\Users\\mahih\\Desktop\\IRP\\Images\\om_resized.jpg')
