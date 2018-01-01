from keras.layers.core import Reshape, Activation, Dropout
from keras.layers.convolutional import Conv2D, UpSampling2D
from keras.layers.pooling import AveragePooling2D
from keras.models import Sequential, model_from_json
import numpy as np
import h5py
from os.path import join

import dataHandler as dataHandler
from resizeLayers import addConvLayers, scale, currentweightsfile

model = Sequential()

model.add(Dropout(0, input_shape=(dataHandler.y, dataHandler.x, 4)))

model = scale(model)

model = addConvLayers(model)

model.compile(loss='mse', optimizer='rmsprop')

# json_file = open(join('imagesmodel', 'resizer_large') + '.json', 'r')
# json_string = json_file.read()
# json_file.close()
model.load_weights(join('imagesmodel', currentweightsfile) + '.h5')

images, names = dataHandler.loadData(dataHandler.findPNG('images/'))

results = model.predict(images, 2)

dataHandler.saveImages(results, names)