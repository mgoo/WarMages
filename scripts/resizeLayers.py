from keras.layers import Conv2D, UpSampling2D, Lambda
import tensorflow as tf

currentweightsfile = 'resizer_4x2'

# def __keepcolorsinbounds(r):
#     print(tf.summary.image('name', r))
    # for batch in range(0, r.shape[0]):
    # for y in range(0, r.shape[1]):
    #     for x in range(0, r.shape[2]):
    #         if r[0][y][x][0] > 0:
    #             r[0][y][x][0] = 0
    #         print(r[0][y][x][0])
    # print(r[0][0][0][0])
    # print(r.shape)
    # return r



def scale(model, amtX=2, amtY=2):
    model.add(UpSampling2D((amtX, amtY)))
    return model

def addConvLayers(model):
    # model.add(Conv2D(4, (3, 3), activation='relu', padding='same'))
    model.add(Conv2D(4, (4, 4), activation='relu', padding='same'))
    model.add(Conv2D(4, (2, 2), activation='relu', padding='same'))
    # model.add(Conv2D(4, (1, 1), activation='relu', padding='same'))
    # model.add(Lambda(__keepcolorsinbounds))
    return model