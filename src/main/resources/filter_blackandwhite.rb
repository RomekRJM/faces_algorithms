require 'fileutils'
require 'shellwords'

FileUtils.remove_dir 'gray'
FileUtils.makedirs 'gray/unknown_photos'

Dir.glob('unknown_photos/**/*'){
  |person_photo|
  grayscale = `convert #{person_photo.shellescape} -colorspace HSL -channel g -separate +channel -format %[fx:mean] info:`
  FileUtils.cp(person_photo, "gray/#{person_photo}#{grayscale}")
  #puts "#{person_photo} - #{grayscale}"
}

