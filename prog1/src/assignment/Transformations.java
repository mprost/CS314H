package assignment;
/**
 *
 * CS314H Programming Assignment 1 - Java image processing
 *
 * Included is the Invert effect from the assignment.  Use this as an
 * example when writing the rest of your transformations.  For
 * convenience, you should place all of your transformations in this file.
 *
 * You can compile everything that is needed with
 * javac -d bin src/assignment/*.java
 *
 * You can run the program with
 * java -cp bin assignment.JIP
 *
 * Please note that the above commands assume that you are in the prog1
 * directory.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * Matt Prost
 * 
 * The Transformations package contains several subclasses of the ImageEffect
 * class. These are used in the JIP file to manipulate images.
 */


class Invert extends ImageEffect {
    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        int width = pixels[0].length;
        int height = pixels.length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = ~pixels[y][x];
            }
        }
        return pixels;
    }
}

/*
 * Dummy class used as a template for other ImageEffect subclasses
 class Dummy extends ImageEffect {

    public Dummy() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("ParamName",
                                           "Description of param.",
                                           10, 0, 1000));
    }

    public int[][] apply(int[][] pixels,
                         ArrayList<ImageEffectParam> params) {
        // Use params here.
        return pixels;
    }
}*/


//This class removes the red from all of the pixels.
class NoRed extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the red 
				//values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(0, getGreen(tempPixel),
						getBlue(tempPixel));
			}
		}
		return pixels;
	}
}

//This class removes the green from all of the pixels.
class NoGreen extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the
				//green values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(getRed(tempPixel), 0,
						getBlue(tempPixel));
			}
		}
		return pixels;
	}
}

//This class removes the blue from all of the pixels.
class NoBlue extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the
				//blue values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(getRed(tempPixel), 
						getGreen(tempPixel), 0);
			}
		}
		return pixels;
	}
}

//This class removes the green and blue from all of the pixels.
class RedOnly extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the
				//green and blue values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(getRed(tempPixel), 0, 0);
			}
		}
		return pixels;
	}
}

//This class removes the red and blue from all of the pixels.
class GreenOnly extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the
				//red and blue values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(0, getGreen(tempPixel), 0);
			}
		}
		return pixels;
	}
}

//This class removes the red and green from all of the pixels.
class BlueOnly extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we store the rgb values of the pixel and set the
				//red and green values to 0.
				tempPixel = pixels[r][c];
				pixels[r][c] = makePixel(0, 0, getBlue(tempPixel));
			}
		}
		return pixels;
	}
}

//This class converts all of the pixels to either black or white depending on
//the luminosity of each pixel.
class BlackAndWhite extends ImageEffect {
	
	public BlackAndWhite() {
        super();
        params = new ArrayList<ImageEffectParam>();
        //This parameter defines whether a pixel is converted to white or black.
        //A pixel with a luminosity (total rgb value) of < this threshold * 3 will 
        //be changed to black, and a pixel with an average rgb value of >= this 
        //threshold * 3 will be changed to white.
        params.add(new ImageEffectParam("Threshold",
                                           "Set a value between 0 and 255 as a "
                                           + "threshold for black vs white in "
                                           + "the output image.\nLarger "
                                           + "thresholds make the image blacker, "
                                           + "and smaller thresholds make the "
                                           + "image whiter.", 127, 0, 255));
    }
	
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int luminosity = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		int threshold = params.get(0).getValue();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//Here we find the total rgb value of a pixel.
				luminosity = getRed(pixels[r][c]) + getGreen(pixels[r][c])
				+ getBlue(pixels[r][c]);
				//If there is a low luminosity, the pixel becomes dark.
				if(luminosity < threshold * 3)
					pixels[r][c] = makePixel(0, 0, 0);
				//If there is a high luminosity, the becomes white.
				else
					pixels[r][c] = makePixel(255, 255, 255);
			}
		}
		return pixels;
	}
}

//This class flips the pixels around a vertical axis.
class VerticalReflect extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			//We only iterate through half of the columns, and then we swap
			//them around the central vertical axis.
			for (int c = 0; c < cols / 2; c++) {
				tempPixel = pixels[r][c];
				pixels[r][c] = pixels[r][cols - 1 - c];
				pixels[r][cols - 1 - c] = tempPixel;
			}
		}
		return pixels;
	}
}

//This class flips the pixels around a horizontal axis.
class HorizontalReflect extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int tempPixel = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		//We only iterate through half of the row, and then we swap them 
		//around the central horizontal axis.
		for (int r = 0; r < rows / 2; r++) {
			for (int c = 0; c < cols; c++) {
				tempPixel = pixels[r][c];
				pixels[r][c] = pixels[rows - 1 - r][c];
				pixels[rows - 1 - r][c] = tempPixel;
			}
		}
		return pixels;
	}
}

//This class doubles the height and width of the image, by replicating the
//original pixel four times.
class Grow extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int rows = pixels.length;
		int cols = pixels[0].length;
		//The apply method returns this larger matrix with the copied pixels.
		int[][] bigPixels = new int[rows * 2][cols * 2];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//The rgb value of the original pixel is replicated into four
				//cells of the larger matrix.
				bigPixels[r * 2][c * 2] = pixels[r][c];
				bigPixels[r * 2][c * 2 + 1] = pixels[r][c];
				bigPixels[r * 2 + 1][c * 2] = pixels[r][c];
				bigPixels[r * 2 + 1][c * 2 + 1] = pixels[r][c];
			}
		}
		return bigPixels;
	}
}

//This class returns a new image with half the width and half the height of the
//original image, by taking the average of four pixels' rgb values and putting 
//them into one cell of the smaller matrix.
class Shrink extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		//The apply method returns this smaller matrix with the averaged pixels.
		int[][] lilPixels = new int[rows / 2][cols / 2];
		for (int r = 0; r < rows / 2; r++) {
			for (int c = 0; c < cols / 2; c++) {
				//This captures the total rgb values of four different pixels
				//in the matrix.
				red = getRed(pixels[r * 2][c * 2]);
				red += getRed(pixels[r * 2][c * 2 + 1]);
				red += getRed(pixels[r * 2 + 1][c * 2]);
				red += getRed(pixels[r * 2 + 1][c * 2 + 1]);
				green = getGreen(pixels[r * 2][c * 2]);
				green += getGreen(pixels[r * 2][c * 2 + 1]);
				green += getGreen(pixels[r * 2 + 1][c * 2]);
				green += getGreen(pixels[r * 2 + 1][c * 2 + 1]);
				blue = getBlue(pixels[r * 2][c * 2]);
				blue += getBlue(pixels[r * 2][c * 2 + 1]);
				blue += getBlue(pixels[r * 2 + 1][c * 2]);
				blue += getBlue(pixels[r * 2 + 1][c * 2 + 1]);
				//The pixels in the final smaller array are the average of 
				//four rgb values from the original image.
				lilPixels[r][c] = makePixel(red / 4, green / 4, blue / 4);
			}
		}
		return lilPixels;
	}
}

//This changes all of the pixels in the image to one of eight colors, based off
//of a threshold.
class Threshold extends ImageEffect {
	
	public Threshold() {
        super();
        params = new ArrayList<ImageEffectParam>();
        params.add(new ImageEffectParam("Threshold", "Set a value between 0 and"
        		+ " 255 as a threshold for color in the output image.",
                                           127, 0, 255));
    }
	
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int threshold = params.get(0).getValue();
		int red = 0;
		int green = 0;
		int blue = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If the rgb values are >= the threshold, the values are
				//changed to 255. If they are < the threshold, then those
				//values are changed to 0.
				if(red < threshold)
					red = 0;
				else 
					red = 255;
				if(green < threshold)
					green = 0;
				else
					green = 255;
				if(blue < threshold)
					blue = 0;
				else 
					blue = 255;
				pixels[r][c] = makePixel(red, green, blue);
			}
		}
		return pixels;
	}
}

//This replaces each pixel with the average rgb value of a 3 x 3 neighborhood.
class Smooth extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int rows = pixels.length;
		int cols = pixels[0].length;
		int red = 0;
		int green = 0;
		int blue = 0;
		int pixelCount = 0;
		//The class returns this new array with the adjusted smooth values.
		int[][] smoothPixels = new int[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				//This adds up all of the rgb values of the center column of 
				//the 3 x 3 neighborhood, for each cell, if the cell exists in 
				//the matrix. We also keep track of the number of cells in the 
				//neighborhood, so that we can average them later.
				pixelCount = 1;
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				if(c > 0)
				{
					red += getRed(pixels[r][c - 1]);
					green += getGreen(pixels[r][c - 1]);
					blue += getBlue(pixels[r][c - 1]);
					pixelCount++;
				}
				if(c < cols - 1)
				{
					red += getRed(pixels[r][c + 1]);
					green += getGreen(pixels[r][c + 1]);
					blue += getBlue(pixels[r][c + 1]);
					pixelCount++;
				}
				
				//This adds up all of the rgb values of the left column of the
				//3 x 3 neighborhood, for each cell, if the cell exists in the
				//matrix. We also keep track of the number of cells in the 
				//neighborhood, so that we can average them later.
				if(r > 0)
				{
					red += getRed(pixels[r - 1][c]);
					green += getGreen(pixels[r - 1][c]);
					blue += getBlue(pixels[r - 1][c]);
					pixelCount++;
					if(c > 0)
					{
						red += getRed(pixels[r - 1][c - 1]);
						green += getGreen(pixels[r - 1][c - 1]);
						blue += getBlue(pixels[r - 1][c - 1]);
						pixelCount++;
					}
					if(c < cols - 1)
					{
						red += getRed(pixels[r - 1][c + 1]);
						green += getGreen(pixels[r - 1][c + 1]);
						blue += getBlue(pixels[r - 1][c + 1]);
						pixelCount++;
					}
				}
				
				//This adds up all of the rgb values of the right column of the
				//3 x 3 neighborhood, for each cell, if the cell exists in the
				//matrix. We also keep track of the number of cells in the 
				//neighborhood, so that we can average them later.
				if(r < rows - 1)
				{
					red += getRed(pixels[r + 1][c]);
					green += getGreen(pixels[r + 1][c]);
					blue += getBlue(pixels[r + 1][c]);
					pixelCount++;
					if(c > 0)
					{
						red += getRed(pixels[r + 1][c - 1]);
						green += getGreen(pixels[r + 1][c - 1]);
						blue += getBlue(pixels[r + 1][c - 1]);
						pixelCount++;
					}
					if(c < cols - 1)
					{
						red += getRed(pixels[r + 1][c + 1]);
						green += getGreen(pixels[r + 1][c + 1]);
						blue += getBlue(pixels[r + 1][c + 1]);
						pixelCount++;
					}
				}
				
				//This makes a smooth pixel out of the average rgb values of
				//the 3 x 3 neighborhood.
				smoothPixels[r][c] = makePixel(red / pixelCount, green / 
						pixelCount, blue / pixelCount);
			}
		}
		return smoothPixels;
	}
}

//This class replaces each pixel with the minimum rgb value in a 3 x 3 
//neighborhood.
class Erode extends ImageEffect {
	
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int rows = pixels.length;
		int cols = pixels[0].length;
		int minPixel = 0;
		int minLuminosity = 0;
		int tempLuminosity = 0;
		
		//The class will return this new matrix with the low luminosity, eroded
		//pixels.
		int[][] erodedPixels = new int[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				//The process of finding the minimum rgb value in the 
				//neighborhood involves finding the luminosity which is the 
				//total rgb value for each pixel. 
				
				//This next block of code sets the current pixel as the minimum
				//pixel, and then it compares it to the other two pixels in the
				//central column of the 3 x 3 neighborhood, if they exist.
				minPixel = pixels[r][c];
				minLuminosity = getRed(pixels[r][c]) + getGreen(pixels[r][c] +
						getBlue(pixels[r][c]));
				if(c > 0)
				{
					tempLuminosity = getRed(pixels[r][c - 1]) + 
							getGreen(pixels[r][c - 1]) + 
							getBlue(pixels[r][c - 1]);
					if(minLuminosity > tempLuminosity)
					{
						minLuminosity = tempLuminosity;
						minPixel = pixels[r][c - 1];
					}
				}
				if(c < cols - 1)
				{
					tempLuminosity = getRed(pixels[r][c + 1]) + 
							getGreen(pixels[r][c + 1]) + 
							getBlue(pixels[r][c + 1]);
					if(minLuminosity > tempLuminosity)
					{
						minLuminosity = tempLuminosity;
						minPixel = pixels[r][c + 1];
					}
				}
				
				//This compares the minimum pixel to the two pixels in the left
				//column of the 3 x 3 neighborhood, if they exist.
				if(r > 0)
				{
					tempLuminosity = getRed(pixels[r - 1][c]) + 
							getGreen(pixels[r - 1][c]) + 
							getBlue(pixels[r - 1][c]);
					if(minLuminosity > tempLuminosity)
					{
						minPixel = pixels[r - 1][c];
						minLuminosity = getRed(pixels[r - 1][c]) + 
								getGreen(pixels[r - 1][c] +
								getBlue(pixels[r - 1][c]));
					}
					if(c > 0)
					{
						tempLuminosity = getRed(pixels[r - 1][c - 1]) + 
								getGreen(pixels[r - 1][c - 1]) + 
								getBlue(pixels[r - 1][c - 1]);
						if(minLuminosity > tempLuminosity)
						{
							minLuminosity = tempLuminosity;
							minPixel = pixels[r - 1][c - 1];
						}
					}
					if(c < cols - 1)
					{
						tempLuminosity = getRed(pixels[r - 1][c + 1]) + 
								getGreen(pixels[r - 1][c + 1]) + 
								getBlue(pixels[r - 1][c + 1]);
						if(minLuminosity > tempLuminosity)
						{
							minLuminosity = tempLuminosity;
							minPixel = pixels[r - 1][c + 1];
						}
					}
				}
				
				//This compares the minimum pixel to the two pixels in the 
				//right column of the 3 x 3 neighborhood, if they exist.
				if(r < rows - 1)
				{
					tempLuminosity = getRed(pixels[r + 1][c]) + 
							getGreen(pixels[r + 1][c]) + 
							getBlue(pixels[r + 1][c]);
					if(minLuminosity > tempLuminosity)
					{
						minPixel = pixels[r + 1][c];
						minLuminosity = getRed(pixels[r + 1][c]) + 
								getGreen(pixels[r + 1][c] +
								getBlue(pixels[r + 1][c]));
					}
					if(c > 0)
					{
						tempLuminosity = getRed(pixels[r + 1][c - 1]) + 
								getGreen(pixels[r + 1][c - 1]) + 
								getBlue(pixels[r + 1][c - 1]);
						if(minLuminosity > tempLuminosity)
						{
							minLuminosity = tempLuminosity;
							minPixel = pixels[r + 1][c - 1];
						}
					}
					if(c < cols - 1)
					{
						tempLuminosity = getRed(pixels[r + 1][c + 1]) + 
								getGreen(pixels[r + 1][c + 1]) + 
								getBlue(pixels[r + 1][c + 1]);
						if(minLuminosity > tempLuminosity)
						{
							minLuminosity = tempLuminosity;
							minPixel = pixels[r + 1][c + 1];
						}
					}
				}
				
				erodedPixels[r][c] = minPixel;
			}
		}
		return erodedPixels;
	}
}

//This class replaces each pixel with the maximum rgb value in a 3 x 3 
//neighborhood.
class Dilate extends ImageEffect {
	
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int rows = pixels.length;
		int cols = pixels[0].length;
		int maxPixel = 0;
		int maxLuminosity = 0;
		int tempLuminosity = 0;
		
		//The class will return this new matrix with the low luminosity, dilated
		//pixels.
		int[][] dilatedPixels = new int[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				
				//The process of finding the maximum rgb value in the 
				//neighborhood involves finding the luminosity which is the 
				//total rgb value for each pixel. 
				
				//This next block of code sets the current pixel as the maximum
				//pixel, and then it compares it to the other two pixels in the
				//central column of the 3 x 3 neighborhood, if they exist.
				maxPixel = pixels[r][c];
				maxLuminosity = getRed(pixels[r][c]) + getGreen(pixels[r][c] +
						getBlue(pixels[r][c]));
				if(c > 0)
				{
					tempLuminosity = getRed(pixels[r][c - 1]) + 
							getGreen(pixels[r][c - 1]) + 
							getBlue(pixels[r][c - 1]);
					if(maxLuminosity < tempLuminosity)
					{
						maxLuminosity = tempLuminosity;
						maxPixel = pixels[r][c - 1];
					}
				}
				if(c < cols - 1)
				{
					tempLuminosity = getRed(pixels[r][c + 1]) + 
							getGreen(pixels[r][c + 1]) + 
							getBlue(pixels[r][c + 1]);
					if(maxLuminosity < tempLuminosity)
					{
						maxLuminosity = tempLuminosity;
						maxPixel = pixels[r][c + 1];
					}
				}
				
				//This compares the maximum pixel to the two pixels in the left
				//column of the 3 x 3 neighborhood, if they exist.
				if(r > 0)
				{
					tempLuminosity = getRed(pixels[r - 1][c]) + 
							getGreen(pixels[r - 1][c]) + 
							getBlue(pixels[r - 1][c]);
					if(maxLuminosity < tempLuminosity)
					{
						maxPixel = pixels[r - 1][c];
						maxLuminosity = getRed(pixels[r - 1][c]) + 
								getGreen(pixels[r - 1][c] +
								getBlue(pixels[r - 1][c]));
					}
					if(c > 0)
					{
						tempLuminosity = getRed(pixels[r - 1][c - 1]) + 
								getGreen(pixels[r - 1][c - 1]) + 
								getBlue(pixels[r - 1][c - 1]);
						if(maxLuminosity < tempLuminosity)
						{
							maxLuminosity = tempLuminosity;
							maxPixel = pixels[r - 1][c - 1];
						}
					}
					if(c < cols - 1)
					{
						tempLuminosity = getRed(pixels[r - 1][c + 1]) + 
								getGreen(pixels[r - 1][c + 1]) + 
								getBlue(pixels[r - 1][c + 1]);
						if(maxLuminosity < tempLuminosity)
						{
							maxLuminosity = tempLuminosity;
							maxPixel = pixels[r - 1][c + 1];
						}
					}
				}
				
				//This compares the minimum pixel to the two pixels in the 
				//right column of the 3 x 3 neighborhood, if they exist.
				if(r < rows - 1)
				{
					tempLuminosity = getRed(pixels[r + 1][c]) + 
							getGreen(pixels[r + 1][c]) + 
							getBlue(pixels[r + 1][c]);
					if(maxLuminosity < tempLuminosity)
					{
						maxPixel = pixels[r + 1][c];
						maxLuminosity = getRed(pixels[r + 1][c]) + 
								getGreen(pixels[r + 1][c] +
								getBlue(pixels[r + 1][c]));
					}
					if(c > 0)
					{
						tempLuminosity = getRed(pixels[r + 1][c - 1]) + 
								getGreen(pixels[r + 1][c - 1]) + 
								getBlue(pixels[r + 1][c - 1]);
						if(maxLuminosity < tempLuminosity)
						{
							maxLuminosity = tempLuminosity;
							maxPixel = pixels[r + 1][c - 1];
						}
					}
					if(c < cols - 1)
					{
						tempLuminosity = getRed(pixels[r + 1][c + 1]) + 
								getGreen(pixels[r + 1][c + 1]) + 
								getBlue(pixels[r + 1][c + 1]);
						if(maxLuminosity < tempLuminosity)
						{
							maxLuminosity = tempLuminosity;
							maxPixel = pixels[r + 1][c + 1];
						}
					}
				}
				
				dilatedPixels[r][c] = maxPixel;
			}
		}
		return dilatedPixels;
	}
}

//This class turns all of the pixels to a shade of gray by changing their
//individual rgb values to the average of their total rgb values.
class GrayScale extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				//This adds up the total rgb values for each pixel.
				totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
						getBlue(pixels[r][c]);
				//This sets the individual rgb values to the average of the 
				//total. This keeps the luminosity the same, but removes the 
				//differences in color between the pixels.
				pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
						totalColor / 3);
			}
		}
		return pixels;
	}
}

//This makes a picture more transparent by adding color proportional to a
//percentage parameter.
class Transparency extends ImageEffect {
	
	public Transparency() {
      super();
      params = new ArrayList<ImageEffectParam>();
      params.add(new ImageEffectParam("Transparency", "Set a value between 0 and"
      		+ " 100% to define the level of transparency.",
                                         50, 0, 100));
  }
	
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int percentage = params.get(0).getValue();
		int red = 0;
		int green = 0;
		int blue = 0;
		int redDifference = 0;
		int greenDifference = 0;
		int blueDifference = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//This calculates the differences between the colors and their
				//maximum values.
				redDifference = 255 - red;
				greenDifference = 255 - green;
				blueDifference = 255 - blue;
				//The differences are then proportioned according to the 
				//transparency percentage and added back to the original pixel.
				redDifference *= percentage;
				greenDifference *= percentage;
				blueDifference *= percentage;
				redDifference /= 100;
				blueDifference /= 100;
				greenDifference /= 100;
				red += redDifference;
				green += greenDifference;
				blue += blueDifference;
				
				pixels[r][c] = makePixel(red, green, blue);
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either red or grayscale, based
//on the dominant color in the pixel.
class GrayWithRed extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If red is not the dominant color in the pixel, then the pixel
				//is converted into grayscale.
				if(red < green || red < blue)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either green, blue, or 
//grayscale, based on the dominant color in the pixel.
class GrayWithNoRed extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If red is the most predominant color in the pixel, then the
				//pixel is converted into grayscale.
				if(red > green && red > blue)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either green or grayscale, 
//based on the dominant color in the pixel.
class GrayWithGreen extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If green is not the dominant color in the pixel, then the pixel
				//is converted into grayscale.
				if(green < red || green < blue)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either red, blue, or 
//grayscale, based on the dominant color in the pixel.
class GrayWithNoGreen extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If green is the most predominant color in the pixel, then the
				//pixel is converted into grayscale.
				if(green > red && green > blue)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either blue or grayscale, 
//based on the dominant color in the pixel.
class GrayWithBlue extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If blue is not the dominant color in the pixel, then the pixel
				//is converted into grayscale.
				if(blue < red || blue < green)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}

//This changes all of the pixels in the image to either red, green, or 
//grayscale, based on the dominant color in the pixel.
class GrayWithNoBlue extends ImageEffect {
	public int[][] apply(int[][] pixels,
			ArrayList<ImageEffectParam> params) {
		int red = 0;
		int green = 0;
		int blue = 0;
		int totalColor = 0;
		int rows = pixels.length;
		int cols = pixels[0].length;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				red = getRed(pixels[r][c]);
				green = getGreen(pixels[r][c]);
				blue = getBlue(pixels[r][c]);
				//If blue is the least predominant color in the pixel, then the
				//pixel is converted into grayscale.
				if(blue > red && blue > green)
				{
					//This adds up the total rgb values for each pixel.
					totalColor = getRed(pixels[r][c]) + getGreen(pixels[r][c]) + 
							getBlue(pixels[r][c]);
					//This sets the individual rgb values to the average of the 
					//total. This keeps the luminosity the same, but removes the 
					//differences in color between the pixels.
					pixels[r][c] = makePixel(totalColor / 3, totalColor / 3, 
							totalColor / 3);
				}
			}
		}
		return pixels;
	}
}
