#!/usr/bin/env python3
"""movie_table module."""
from sqlalchemy import Column, Integer, String, Date, Text
from media import Media

class Anime(Media):
    """Movie table."""

    __tablename__ = "Movie"
    play_time = Column(Integer) # measured in minutes

    def __init__(self, play_time=0):
        """Create a record."""
        self.play_time = play_time
        
