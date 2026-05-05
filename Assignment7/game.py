# CSCE 31903 Programming Paradigms
# Spring 2026
# Assignment 7
# Name: Eduardo Arce Vargas
# Date: 4/30/26
# Assignment Description: The objective of this assignment was to introduce 
#                         us to python and rewrite our prevoius assignment using this new programming language

import pygame
import time
import json
import math
import random
import threading

from pygame.locals import*
from time import sleep


class Sprite():
    # Construcor
    def __init__(self, x, y, w, h, speed=0):
        self.x = x
        self.y = y
        self.w = w
        self.h = h
        self.px = x
        self.py = y
        self.speed = speed
        self.valid = True
        # creates a bounding box around the image
        self.rect = pygame.Rect(x,y,w,h)

    def update(self):
        return self.valid

    # Getters
    def get_x(self):
        return self.x
    
    def get_y(self):
        return self.y
    
    def get_w(self):
        return self.w
    
    def get_h(self):
        return self.h
    
    def get_px(self):
        return self.px
    
    def get_py(self):
        return self.py
    
    def get_speed(self):
        return self.speed
    
    def get_right(self):
        return self.x + self.w
    
    def get_left(self):
        return self.x
    
    def get_top(self):
        return self.y
    
    def get_bottom(self):
        return self.y + self.h
    
    def get_prev_right(self):
        return self.px + self.w
    
    def get_prev_left(self):
        return self.px
    
    def get_prev_top(self):
        return self.py
    
    def get_prev_bottom(self):
        return self.py + self.h
    

    # Setters
    def set_x(self, x):
        self.x = x

    def set_y(self, y):
        self.y = y

    def set_h(self, h):
        self.h = h

    def set_w(self, w):
        self.w = w

    def set_prev_position(self):
        self.px = self.x
        self.py = self.y

    # Fixing Collisions

    def fix_collision(self, other_sprite):
        if(self.get_prev_right() <= other_sprite.get_left() and self.get_right() > other_sprite.get_left()):
            self.x = other_sprite.get_left() - self.w - 1
            return
        
        if(self.get_prev_left() >= other_sprite.get_right() and self.get_left() < other_sprite.get_right()):
            self.x = other_sprite.get_right() + 1
            return 
        
        if(self.get_prev_bottom() <= other_sprite.get_top() and self.get_bottom() > other_sprite.get_top()):
            self.y = other_sprite.get_top() - self.h - 1
            return
        
        if(self.get_prev_top() >= other_sprite.get_bottom() and self.get_top() < other_sprite.get_bottom()):
            self.y = other_sprite.get_bottom() + 1
            return
        
    # Detect if clicking another sprite
    def sprite_detection(self, mouseX, mouseY):
        return mouseX >= self.x and mouseX <= self.x + self.w and mouseY >= self.y and mouseY <= self.y + self.h
        
    def is_ms_pacman(self):
        return False
    
    def is_tile(self):
        return False
    
    def is_fruit(self):
        return False
    
    def is_ghost(self):
        return False
    
    def is_pellet(self):
        return False


class MsPacman(Sprite):

    # Defining static variables and consts
    MAX_DIRECTIONS = 4
    NUM_IMAGES_PER_DIRECTION = 3
    MSPACMAN_HEIGHT = 25
    MSPACMAN_WIDTH = 25
    SPEED = 10
    images = []

    # Constructor
    def __init__(self, start_x, start_y):
        super().__init__(start_x, start_y, MsPacman.MSPACMAN_WIDTH, MsPacman.MSPACMAN_HEIGHT, MsPacman.SPEED)
        self.initialize_vars()

    def initialize_vars(self):
        self.set_prev_position()
        self.numFrame = 0
        self.direction = 0
        self.points = 0

        # Load MsPacman Images
        if(len(MsPacman.images) == 0):
            image_num = 1
            for i in range(MsPacman.MAX_DIRECTIONS):
                MsPacman.images.append([])
                for j in range(MsPacman.NUM_IMAGES_PER_DIRECTION):
                    MsPacman.images[i].append(
                        pygame.image.load("images/mspacman" + str(image_num) + ".png")
                    )
                    image_num += 1

    # Functionality methods
    def draw_yourself(self, screen):
        LOCATION = (self.x, self.y - View.get_scroll_pos())
        SIZE = (self.w, self.h)
        screen.blit(pygame.transform.scale(MsPacman.images[self.direction][self.numFrame], SIZE), LOCATION)


    def marshal(self):
        return {
            "x": self.x,
            "y": self.y
        }

    def add_points(self, x):
        self.points += x

    def get_points(self):
        return self.points
    
    def update(self):
        return True
    
    def is_ms_pacman(self):
        return True
    
    # MsPacman Movement Control

    def update_image_num(self):
        self.numFrame += 1

        if self.numFrame >= MsPacman.NUM_IMAGES_PER_DIRECTION:
            self.numFrame = 0

    def move_right(self):
        if self.get_x() > 756:
            self.set_x(-5)
        else:
            self.direction = 2
            self.update_image_num()
            self.set_x(self.get_x() + self.get_speed())

    def move_left(self):
        if self.get_x() < -10:
            self.set_x(776)
        else:
            self.direction = 0
            self.update_image_num()
            self.set_x(self.get_x() - self.get_speed())

    def move_up(self):
        self.direction = 1
        self.update_image_num()
        self.set_y(self.get_y() - self.get_speed())

        View.follow_ms_pacman(self.get_y())

    def move_down(self):
        self.direction = 3
        self.update_image_num()
        self.set_y(self.get_y() + self.get_speed())

        View.follow_ms_pacman(self.get_y())


class Tile(Sprite):

    # Declaring static variables 
    image = None
    TILE_HEIGHT = 40
    TILE_WIDTH = 40

    # Constructor
    def __init__(self, x, y):
        super().__init__(x, y, Tile.TILE_HEIGHT, Tile.TILE_WIDTH)
        self.initialize_vars()

    def initialize_vars(self):
        if Tile.image == None:
            Tile.image = pygame.image.load("images/tile2.png")

    # Functionality methods
    def marshal(self):
        return {
            "x": self.x,
            "y": self.y
        }
    
    def draw_yourself(self, screen):
        LOCATION = (self.x, self.y - View.get_scroll_pos())
        SIZE = (self.w, self.h)
        screen.blit(pygame.transform.scale(Tile.image, SIZE), LOCATION)

    def update(self):
        return True
    
    def is_tile(self):
        return True
    

class Fruit(Sprite):

    # Declaring static variables
    FRUIT_HEIGHT = 25
    FRUIT_WIDTH = 25
    FRUIT_COUNT = 7
    images = []
    FRUIT_SPEED = 8

    # Constructor
    def __init__(self, x, y, fruitNum):
        super().__init__(x, y, Fruit.FRUIT_HEIGHT, Fruit.FRUIT_WIDTH, Fruit.FRUIT_SPEED)
        self.initialize_vars(fruitNum)

    def initialize_vars(self, fruitNum):
        self.fruit_num = fruitNum
        self.eaten = False
        self.direction = 0
        self.get_random_direction()
        self.set_prev_position()

        if len(Fruit.images) == 0:
            image_num = 1

            for i in range(Fruit.FRUIT_COUNT):
                Fruit.images.append(pygame.image.load(
                    "images/fruit" + str(image_num) + ".png"
                ))
                image_num += 1

    # Getters    
    def get_fruit_num(self):
        return self.fruit_num
    
    def get_random_direction(self):
        
        next_dir = random.randint(0, 3)
        while self.direction == next_dir:
            next_dir = random.randint(0, 3)

        self.direction = next_dir

    def get_eaten(self):
        return self.eaten

    def been_eaten(self):
        self.eaten = True

    def draw_yourself(self, screen):
        LOCATION = (self.x, self.y - View.get_scroll_pos())
        SIZE = (self.w, self.h)
        screen.blit(pygame.transform.scale(Fruit.images[self.get_fruit_num()], SIZE), LOCATION)

    # Fruit Movement Control
    def move_right(self):
        if self.get_x() > 756:
            self.set_x(-5)

        else:
            self.set_x(self.get_x() + self.get_speed())

    def move_left(self):
        if self.get_x() < -10:
            self.set_x(776)

        else:
            self.set_x(self.get_x() - self.get_speed())

    def move_up(self):
        self.set_y(self.get_y() - self.get_speed())

    def move_down(self):
        self.set_y(self.get_y() + self.get_speed())

    def is_fruit(self):
        return True
    
    def marshal(self):
        return {
            "x": self.x,
            "y": self.y,
            "fruitNum": self.fruit_num
        }
    
    def update(self):
        if self.eaten == False:
            match self.direction:
                case 0:
                    self.move_right()
                case 1:
                    self.move_up()
                case 2:
                    self.move_left()
                case 3:
                    self.move_down()
            
            return True
        return False
    

class Ghost(Sprite):

    # Declaring static variables
    GHOST_COUNT = 5
    MAX_DIRECTIONS = 4
    GHOST_HEIGHT = 25
    GHOST_WIDTH = 25
    GHOST_SPEED = 10
    NUM_IMAGES_PER_DIRECTION = 2
    STATE_DURATION = 1
    images = []

    # Constructor
    def __init__(self, x, y, ghostNum):
        super().__init__(x, y, Ghost.GHOST_HEIGHT, Ghost.GHOST_WIDTH, Ghost.GHOST_SPEED)
        self.initialize_vars(ghostNum)

    def initialize_vars(self, ghostNum):
        self.eaten = False
        self.ghost_num = ghostNum
        self.ready_to_remove = False
        self.direction = 0
        self.num_frame = 0
        ghost_names = ["blinky", "inky", "pinky", "sue", "ghost"]
        self.get_random_direction()
        self.set_prev_position()

        if len(Ghost.images) == 0:
            for i in range(Ghost.GHOST_COUNT):
                Ghost.images.append([])
                imageNum = 1
                file_name = "images/" + ghost_names[i]

                for j in range(Ghost.MAX_DIRECTIONS):
                    Ghost.images[i].append([])

                    for k in range(Ghost.NUM_IMAGES_PER_DIRECTION):
                        Ghost.images[i][j].append(
                            pygame.image.load(file_name + str(imageNum) + ".png")
                        )
                        imageNum += 1

    # Getters
    def get_random_direction(self):
        
        next_dir = random.randint(0, 3)
        while self.direction == next_dir:
            next_dir = random.randint(0, 3)

        self.direction = next_dir

    def get_ghost_num(self):
        return self.ghost_num
    
    def is_eaten(self):
        return self.eaten
    
    # Functionality methods
    def draw_yourself(self, screen):
        LOCATION = (self.x, self.y - View.get_scroll_pos())
        SIZE = (self.w, self.h)
        screen.blit(pygame.transform.scale(Ghost.images[self.ghost_num][self.direction][self.num_frame], SIZE), LOCATION)

    def update_image_num(self):
        self.num_frame += 1
        if self.num_frame >= Ghost.NUM_IMAGES_PER_DIRECTION:
            self.num_frame = 0

    # Ghost Movement Control
    def move_right(self):
        self.direction = 2
        self.update_image_num()

        if self.get_x() > 756:
            self.set_x(-5)
        else:
            self.set_x(self.get_x() + self.get_speed())

    def move_left(self):
        self.direction = 0
        self.update_image_num()

        if self.get_x() < -10:
            self.set_x(776)
        else: 
            self.set_x(self.get_x() - self.get_speed())

    def move_up(self):
        self.direction = 1
        self.update_image_num()
        self.set_y(self.get_y() - self.get_speed())

    def move_down(self):
        self.direction = 3
        self.update_image_num()
        self.set_y(self.get_y() + self.get_speed())

    # Eaten Animation Control
    def set_blue(self):
        self.direction = 0
        self.ghost_num = 4

    def set_white(self):
        self.direction = 1

    def set_eyes(self):
        self.direction = 2

    def eaten_animation(self):
        stateDuration = 1

        self.eaten = True
        self.set_blue()

        def to_white():
            self.set_white()

            def to_eyes():
                self.set_eyes()

                def remove():
                    self.ready_to_remove = True

                threading.Timer(stateDuration, remove).start()

            threading.Timer(stateDuration, to_eyes).start()

        threading.Timer(stateDuration, to_white).start()
        
    def marshal(self):
        return {
            "x": self.x,
            "y": self.y,
            "ghostNum": self.ghost_num
        }

    def is_ghost(self):
        return True
    
    def update(self):
        if self.ready_to_remove:
            return False
        
        if self.eaten == False:
            match self.direction:
                case 0:
                    self.move_left()
                    return True
                case 1:
                    self.move_up()
                    return True
                case 2:
                    self.move_right()
                    return True
                case 3:
                    self.move_down()
                    return True
        else:
            self.update_image_num()

        return True
    
class Pellet(Sprite):

    # Declaring static variables
    PELLET_SIZE = 40
    image = None

    # Constructor
    def __init__(self, x, y):
        super().__init__(x, y, Pellet.PELLET_SIZE, Pellet.PELLET_SIZE)
        self.initialize_vars()

    def initialize_vars(self):
        self.eaten = False
        if Pellet.image == None:
            Pellet.image = pygame.image.load("images/pellet1.png")

    def marshal(self):
        return {
            "x": self.x,
            "y": self.y
        }

    # Getters  
    def get_eaten(self):
        return self.eaten
    
    # Functionality methods
    def been_eaten(self):
        self.eaten = True
    
    def draw_yourself(self, screen):
        LOCATION = (self.x, self.y - View.get_scroll_pos())
        SIZE = (self.w, self.h)
        screen.blit(pygame.transform.scale(Pellet.image, SIZE), LOCATION)

    def update(self):
        if not self.eaten:
            return True
        return False
    
    def is_pellet(self):
        return True
    


class Model():
    # Declaring static variables
    filename = "map.json"
    items_i_can_add = []
    edit_mode = False
    add_map_item = True
    item_num = 0
    AVAILABLE_GHOSTS = 4
    AVAILABLE_FRUITS = 7
    EDIT_MAP_COORDINATES = 30

    # Constructor 
    def __init__(self):
        self.load_map()
        self.set_items_list()

    # Setting up list for Edit Mode
    def set_items_list(self):
        Model.items_i_can_add = []

        Model.items_i_can_add.append(Tile(Model.EDIT_MAP_COORDINATES, Model.EDIT_MAP_COORDINATES))
        pellet = Pellet(Model.EDIT_MAP_COORDINATES, Model.EDIT_MAP_COORDINATES)
        Model.items_i_can_add.append(pellet)

        for i in range(Model.AVAILABLE_GHOSTS):
            ghost = Ghost(Model.EDIT_MAP_COORDINATES, Model.EDIT_MAP_COORDINATES, i)
            ghost.set_h(40)
            ghost.set_w(40)
            Model.items_i_can_add.append(ghost)
        
        for j in range(Model.AVAILABLE_FRUITS):
            fruit = Fruit(Model.EDIT_MAP_COORDINATES, Model.EDIT_MAP_COORDINATES, j)
            fruit.set_h(40)
            fruit.set_w(40)
            Model.items_i_can_add.append(fruit)

    # Load map from JSON File
    def load_map(self):
        
        self.sprites = []

        with open(Model.filename) as file:
            data = json.load(file)

            ms_pacman = data["msPacman"]
            tiles = data["tiles"]
            fruits = data["fruits"]
            ghosts = data["ghosts"]
            pellets = data["pellets"]
        file.close()
        
        self.ms_pacman = MsPacman(ms_pacman["x"], ms_pacman["y"])
        self.sprites.append(self.ms_pacman)

        for entry in tiles:
            self.sprites.append(Tile(entry["x"], entry["y"]))
        for entry in pellets:
            self.sprites.append(Pellet(entry["x"], entry["y"]))
        for entry in fruits:
            self.sprites.append(Fruit(entry["x"], entry["y"], entry["fruitNum"]))
        for entry in ghosts:
            self.sprites.append(Ghost(entry["x"], entry["y"], entry["ghostNum"]))

    # Save map in JSON file
    def save_map(self):
        # create lists for each type of sprite you want to save
        tiles = []
        fruits = []
        ghosts = []
        pellets = []

        # go through all of the sprites, saving them into the 
        # appropriate lists
        for s in self.sprites:
            if s.is_tile():
                tiles.append(s.marshal())
            elif s.is_fruit():
                fruits.append(s.marshal())
            elif s.is_ghost():
                ghosts.append(s.marshal())
            elif s.is_pellet():
                pellets.append(s.marshal())

        # create the dictionary of sprites, split by what types
        # they are - rocks and lilypads are lists, while 
        # turtlex and turtley are singular attributes
        map_to_save = {
            "tiles": tiles,
            "fruits": fruits,
            "ghosts": ghosts,
            "pellets": pellets,
            "msPacman": {"x": self.ms_pacman.get_x(), "y": self.ms_pacman.get_y()}
        }

        # Save to file
        with open(Model.filename, "w") as f:
            json.dump(map_to_save, f)

    # Getters
    def get_sprites_count(self):
        return len(self.sprites)
    
    def get_sprite(self, index):
        return self.sprites[index]
    
    def get_ms_pacman(self):
        return self.ms_pacman
    
    def get_edit_mode(self):
        return Model.edit_mode
    
    def get_add_map_item(self):
        return Model.add_map_item
    
    def get_items_count(self):
        return len(Model.items_i_can_add)
    
    def get_item(self):
        return Model.items_i_can_add[Model.item_num]
    
    def change_item_num(self):
        Model.item_num += 1

        if Model.item_num > len(Model.items_i_can_add) - 1:
            Model.item_num = 0
            return

    # setters
    def set_edit_mode(self, value):
        Model.edit_mode = value

    def set_add_map_item(self, value):
        Model.add_map_item = value

    # Add and Remove Sprites from map
    def clear_map(self):
        self.sprites.clear()

    # ADD SPRITES
    def add_tile(self, pos):
        x = (pos[0] // 40) * 40
        y = ((pos[1] + View.get_scroll_pos()) // 40) * 40
        
        for sprite in self.sprites:
            if sprite.sprite_detection(x + 1, y + 1):
                return
            
        tempSprite = Tile(x, y)
        self.sprites.append(tempSprite)

    def add_fruit(self, pos, fruit_num):
        x = (pos[0] // 40) * 40
        y = ((pos[1] + View.get_scroll_pos()) // 40) * 40
        
        for sprite in self.sprites:
            if sprite.sprite_detection(x + 1, y + 1):
                return
            
        f = Fruit(x, y, fruit_num)
        self.sprites.append(f)

    def add_ghost(self, pos, ghost_num):
        x = (pos[0] // 40) * 40
        y = ((pos[1] + View.get_scroll_pos()) // 40) * 40
        
        for sprite in self.sprites:
            if sprite.sprite_detection(x + 1, y + 1):
                return
            
        g = Ghost(x, y, ghost_num)
        self.sprites.append(g)

    def add_pellet(self, pos):
        x = (pos[0] // 40) * 40
        y = ((pos[1] + View.get_scroll_pos()) // 40) * 40
        
        for sprite in self.sprites:
            if sprite.sprite_detection(x + 1, y + 1):
                return
            
        p = Pellet(x, y)
        self.sprites.append(p)

    # REMOVE SPRITES
    def remove_tile(self, pos):
        for sprite in self.sprites:
            if sprite.is_tile():
                if sprite.sprite_detection(pos[0], pos[1]):
                    self.sprites.remove(sprite)
                    return 
                
    def remove_ghost(self, pos):
        for sprite in self.sprites:
            if sprite.is_ghost():
                if sprite.sprite_detection(pos[0], pos[1]):
                    self.sprites.remove(sprite)
                    return 
                
    def remove_fruit(self, pos):
        for sprite in self.sprites:
            if sprite.is_fruit():
                if sprite.sprite_detection(pos[0], pos[1]):
                    self.sprites.remove(sprite)
                    return 

    def remove_pellet(self, pos):
        for sprite in self.sprites:
            if sprite.is_pellet():
                if sprite.sprite_detection(pos[0], pos[1]):
                    self.sprites.remove(sprite)
                    return 

    # Detect Collisions between sprites
    def detect_collision(self, sprite, other_sprite):
        if sprite.get_right() <= other_sprite.get_left():
            return False
        if sprite.get_left() >= other_sprite.get_right():
            return False
        if sprite.get_bottom() <= other_sprite.get_top():
            return False
        if sprite.get_top() >= other_sprite.get_bottom():
            return False
        
        return True

    def update(self):
        for sprite in self.sprites:

            # Setting previous position
            if sprite.is_fruit() or sprite.is_ghost():
                sprite.set_prev_position()

            # Remove unnnecesary Sprites
            if sprite.update() == False:
                self.sprites.remove(sprite)

            # Detecting Collisions
            for other_sprite in self.sprites:
                if other_sprite != sprite and self.detect_collision(sprite, other_sprite):

                    if sprite.is_ms_pacman() and other_sprite.is_tile():
                        sprite.fix_collision(other_sprite)

                    elif sprite.is_fruit() and other_sprite.is_tile():
                        sprite.fix_collision(other_sprite)
                        sprite.get_random_direction()

                    elif sprite.is_fruit() and other_sprite.is_ms_pacman():
                        if sprite.get_eaten() == False:
                            other_sprite.add_points(1)
                        sprite.been_eaten()

                    elif sprite.is_ghost() and other_sprite.is_tile():
                        sprite.fix_collision(other_sprite)
                        sprite.get_random_direction()

                    elif sprite.is_ms_pacman() and other_sprite.is_ghost():
                        if other_sprite.is_eaten() == False:
                            sprite.add_points(5)
                            other_sprite.eaten_animation()
                    
                    elif sprite.is_ms_pacman() and other_sprite.is_pellet():
                        if not other_sprite.get_eaten():
                            sprite.add_points(2)
                        other_sprite.been_eaten()


class View():

    # Declaring Static variables
    WINDOW_HEIGHT = 800
    WINDOW_WIDTH = 760
    MAP_HEIGHT = 960
    scroll_position = 0

    # Constructor
    def __init__(self, model):
        SCREEN_SIZE = (View.WINDOW_WIDTH, View.WINDOW_HEIGHT)
        self.screen = pygame.display.set_mode(SCREEN_SIZE, 32)
        self.model = model

    def update(self):
        self.ms_pacman = self.model.get_ms_pacman()
        self.screen.fill([1, 5, 66]) #dark blue bg

        # draw sprites to the screen
        for sprite in self.model.sprites:
            sprite.draw_yourself(self.screen)

        # Display points
        font = pygame.font.SysFont(None, 32)   
        text_string = "Points: " + str(self.ms_pacman.get_points())
        YELLOW_COLOR = (255, 215, 0)
        text_surface = font.render(text_string, True, YELLOW_COLOR)
        TEXT_LOCATION = (10, 10)
        self.screen.blit(text_surface, TEXT_LOCATION)

        # Display Edit Mode
        if self.model.get_edit_mode():

            sprite = self.model.get_item()

            if self.model.get_add_map_item():
                pygame.draw.rect(self.screen, (43, 203, 54), (0, 0, 100, 100))
            else:
                pygame.draw.rect(self.screen, (203, 43, 43), (0, 0, 100, 100))

            # Draw Sprite selection
            sprite.draw_yourself(self.screen)

        
        # update display screen
        pygame.display.flip()

    @staticmethod
    def follow_ms_pacman(ms_pacman_y):
        target = View.WINDOW_HEIGHT / 2

        View.scroll_position = ms_pacman_y - target

        if View.scroll_position < 0:
            View.scroll_position = 0

        max_scroll = View.MAP_HEIGHT - View.WINDOW_HEIGHT
        if View.scroll_position > max_scroll:
            View.scroll_position = max_scroll
        

    @staticmethod
    def get_scroll_pos():
        return View.scroll_position

class Controller():
    
    # Constructor 
    def __init__(self, model, view):
        self.model = model
        self.view = view
        self.keep_going = True

    def update(self):

        self.ms_pacman = self.model.get_ms_pacman()

        for event in pygame.event.get():
            if event.type == QUIT:
                self.keep_going = False
            elif event.type == KEYDOWN:
                if event.key == K_ESCAPE or event.key == K_q:
                    self.keep_going = False

            elif event.type == pygame.MOUSEBUTTONUP:

                # Add Sprites
                if self.model.get_edit_mode() and self.model.get_add_map_item():

                    if self.model.get_item().is_tile():
                        self.model.add_tile(pygame.mouse.get_pos())

                    if self.model.get_item().is_pellet():
                        self.model.add_pellet(pygame.mouse.get_pos())

                    if self.model.get_item().is_ghost():
                        ghost = self.model.get_item()
                        self.model.add_ghost(pygame.mouse.get_pos(), ghost.get_ghost_num())
                    
                    if self.model.get_item().is_fruit():
                        fruit = self.model.get_item()
                        self.model.add_fruit(pygame.mouse.get_pos(), fruit.get_fruit_num())
                
                # Remove Sprites
                if self.model.get_edit_mode() and not self.model.get_add_map_item():

                    if self.model.get_item().is_tile():
                        self.model.remove_tile(pygame.mouse.get_pos())

                    if self.model.get_item().is_pellet():
                        self.model.remove_pellet(pygame.mouse.get_pos())

                    if self.model.get_item().is_ghost():
                        self.model.remove_ghost(pygame.mouse.get_pos())

                    if self.model.get_item().is_fruit():
                        self.model.remove_fruit(pygame.mouse.get_pos())

            elif event.type == pygame.MOUSEMOTION and event.buttons[0]:
                # Add Sprites
                if self.model.get_edit_mode() and self.model.get_add_map_item():

                    if self.model.get_item().is_tile():
                        self.model.add_tile(pygame.mouse.get_pos())

                    if self.model.get_item().is_pellet():
                        self.model.add_pellet(pygame.mouse.get_pos())

                    if self.model.get_item().is_ghost():
                        ghost = self.model.get_item()
                        self.model.add_ghost(pygame.mouse.get_pos(), ghost.get_ghost_num())
                    
                    if self.model.get_item().is_fruit():
                        fruit = self.model.get_item()
                        self.model.add_fruit(pygame.mouse.get_pos(), fruit.get_fruit_num())
                
                # Remove Sprites
                if self.model.get_edit_mode() and not self.model.get_add_map_item():

                    if self.model.get_item().is_tile():
                        self.model.remove_tile(pygame.mouse.get_pos())

                    if self.model.get_item().is_pellet():
                        self.model.remove_pellet(pygame.mouse.get_pos())

                    if self.model.get_item().is_ghost():
                        self.model.remove_ghost(pygame.mouse.get_pos())

                    if self.model.get_item().is_fruit():
                        self.model.remove_fruit(pygame.mouse.get_pos())

            # Key Control
            elif event.type == pygame.KEYUP: #this is keyReleased!
                if event.key == K_c:
                    self.model.clear_map()
                    print("Map cleared and game reset")
                if event.key == K_z and self.model.get_edit_mode():
                    self.model.change_item_num()
                if event.key == K_e:
                    self.model.set_edit_mode(not self.model.get_edit_mode())
                if event.key == K_a and self.model.get_edit_mode():
                    self.model.set_add_map_item(not self.model.get_add_map_item())
                if event.key == K_l:
                    self.model.load_map()
                    print("Map loaded")
                if event.key == K_s:
                    self.model.save_map()
                    print("Map saved")

            elif event.type == pygame.MOUSEBUTTONDOWN:
                self.mouse_motion = True

        keys = pygame.key.get_pressed()
        self.ms_pacman.set_prev_position()
        if keys[K_LEFT]:
            self.ms_pacman.move_left()
        if keys[K_RIGHT]:
            self.ms_pacman.move_right()
        if keys[K_UP]:
            self.ms_pacman.move_up()
        if keys[K_DOWN]:
            self.ms_pacman.move_down()

    

print("Use the arrow keys to move. Press Esc to quit.")
print("Press e to enter edit mode. Press a to switch between add and remove")
print("Press z to change the sprite you want to add/remove")
pygame.init()
pygame.font.init()
m = Model()
v = View(m)
c = Controller(m, v)
while c.keep_going:
    c.update()
    m.update()
    v.update()
    sleep(0.04)
print("Goodbye!")