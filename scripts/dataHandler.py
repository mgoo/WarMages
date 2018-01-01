from os import listdir, mkdir
from os.path import isfile, join, isdir
import numpy as np
import time
from multiprocessing import Pool

import scipy
from scipy.misc import imsave, imresize
from scipy import misc

y = 1344
x = 832
# y = 72
# x = 72
smlX = int(x/2)
smlY = int(y/2)

def recurseFindPNG(dir):
    pngs = []
    for f in listdir(dir):
        if isfile(join(dir, f)) and ('.png' in f or '.jpg' in f):
            pngs.append(join(dir, f))
        elif isdir(join(dir, f)):
            pngs += recurseFindPNG(join(dir, f))
    return pngs

def findPNG(dir):
    pngs = []
    for f in listdir(dir):
        if isfile(join(dir, f)) and '.png' in f:
            pngs.append(join(dir, f))
    return pngs

def  loadData(imageFiles, resize = False, limit=-1):
    images = []
    imagenames = []
    if limit == -1:
        limit = len(imageFiles)
    for imageNameIdx in range(0, limit):
        try:
            image = misc.imread(imageFiles[imageNameIdx])
        except:
            continue
        imagedata = np.array(image)

        # ignore images that are not the correct dimesion
        if imagedata.shape != (y, x, 4) and resize == False:
            continue
        elif resize: # Resize images that are not the correct dimesions
            image = imresize(imagedata, (y, x))
            imagedata = np.array(image)

        # Add alpha values to images with no alpha
        if (imagedata.shape == (y, x, 3)):
            imagedata = np.concatenate((imagedata, np.zeros((y, x, 1))), axis=2)

        images.append(imagedata)
        imagenames.append(imageFiles[imageNameIdx].split('/')[-1])
    return [np.array(images), imagenames]

def saveImages(images, names):
    dir = repr(time.time()).split('.')[0]
    mkdir('imagesout/' + dir)
    for n in range(0, images.shape[0]):
        imagedata = images[n]
        for yIdn in range(0, len(imagedata)):
            for xIdn in range(0, len(imagedata[yIdn])):
                for channel in range(0, 4):
                    if imagedata[yIdn][xIdn][channel] > 255:
                        imagedata[yIdn][xIdn][channel] = 254.9
                    if imagedata[yIdn][xIdn][channel] < 0:
                        imagedata[yIdn][xIdn][channel] = 0
                # imagedata[yIdn][xIdn][3] = imagedata[yIdn][xIdn][3] / 255
        scipy.misc.toimage(imagedata.astype('uint8')).save('imagesout/' + dir + '/' + names[n])

def loadSpriteSheet(spritesheetFiles, spritewidth=64, spriteheight=64):
    spritesheetimages, spritesheetnames = loadData(spritesheetFiles)
    for spritesheetimage in spritesheetimages:
        spritesheetimage = np.array(spritesheetimage)
        xsprites = int(spritesheetimage.shape[1] / spritewidth)
        ysprites = int(spritesheetimage.shape[0] / spriteheight)
        spritesheetimages.reshape((xsprites, spritesheetimage.shape[0], spritewidth, 4))



