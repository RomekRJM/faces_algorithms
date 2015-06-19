# coding=utf-8
from os.path import join, getsize, isfile
from os import listdir

photos_dir = 'photos/'

array = []
biggest_files = []
for country_dir_name in listdir(photos_dir):
    country_dir = join(photos_dir, country_dir_name)
    if isfile(country_dir):
        continue
      
    dir_size = 0
    photos_inside = 0
    
    for photo in listdir(country_dir):
        photos_inside += 1
        file_name = join(country_dir, photo)
        file_size = getsize(file_name)
        dir_size += file_size
        biggest_files.append((file_size, str(file_name)))

    array.append((photos_inside, dir_size, str(join(country_dir,country_dir_name))))


print sorted(array)
# print sorted(biggest_files)