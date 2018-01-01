from keras.layers.core import Reshape, Activation, Dropout
from keras.layers.pooling import AveragePooling2D
from keras.models import Sequential, model_from_json
import numpy as np
import matplotlib.pyplot as plt
import h5py
from os.path import join
import dataHandler as dataHandler
from resizeLayers import scale, addConvLayers, currentweightsfile

model = Sequential()

model.add(AveragePooling2D(pool_size=(2,2),
                           strides=(2,2),
                           padding='same',
                           input_shape=(dataHandler.y, dataHandler.x, 4)))
model = scale(model)
# model.add(Dropout(0.02))
model = addConvLayers(model)

model.compile(loss='mse', optimizer='rmsprop')
# model.compile(loss='mse', optimizer='adam')

trainingdata = dataHandler.loadData(dataHandler.recurseFindPNG('images/'))[0]
# trainingdata = dataHandler.loadData(dataHandler.findPNG('images/'))[0]

history = model.fit(trainingdata, trainingdata, batch_size=5, epochs=100)
#
# modelJSON = model.to_json()
# with open(join('imagesmodel', 'resizer_large') + '.json', 'w') as json_file:
#     json_file.write(modelJSON)
model.save_weights(join('imagesmodel', currentweightsfile) + '.h5')

plt.plot(history.history['loss'])
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')
plt.show()