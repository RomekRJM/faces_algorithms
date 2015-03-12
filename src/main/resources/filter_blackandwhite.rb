require 'fileutils'
require 'shellwords'

def isMonochromatic(image_file)
  mono = 0
  color = 0
  `convert #{image_file.shellescape} -colors 8 -depth 8 -format %c histogram:info:`.each_line {
    |line|
    line_splitted = line.split(/[\s:(),]/)
    red = line_splitted[-3].to_i
    green = line_splitted[-2].to_i
    blue = line_splitted[-1].to_i
    diff = ((red - green).abs + (red - blue).abs + (green - blue).abs).to_f / 3.0
    #puts line
  
    if diff < 20
      #puts "#{red}, #{green}, #{blue}, #{diff}"
      #puts "#{image_file} diff: #{diff}"
      #return true
      mono = mono + 1
    else
      color = color + 1
    end
  }
  #puts "#{image_file} is not monochromatic"
  return mono > color
end

def isGrayscale(image_file)
  if `convert #{image_file.shellescape} -colorspace HSL -channel g -separate +channel -format %[fx:mean] info:`.to_f < 0.1
    return true
  else
    return false
  end
end

CHECKEDDIR = 'photos'

FileUtils.remove_dir 'gray'
FileUtils.makedirs 'gray/' + CHECKEDDIR

Dir.glob(CHECKEDDIR + '/**/*'){
  |file|
  if File.stat(file).file?
    if isGrayscale file
      #monochromatic = isMonochromatic file
      newFile = File.basename(file)
      FileUtils.cp(file, "gray/#{newFile}")
      #puts "#{file} - #{grayscale}"
    end
  end
}