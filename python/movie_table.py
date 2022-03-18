#!/usr/bin/env python3
"""movie_table module."""
from sqlalchemy import Column, Integer, ForeignKey
from python.media_table import Media, table_names

class Movie(Media):
    """Movie table."""

    __tablename__ = "Movie"
    id = Column(Integer, ForeignKey('Media.id'), primary_key=True)
    play_time = Column(Integer) # measured in minutes

    __mapper_args__ = {
        'polymorphic_identity':'movie',
    }

    def __init__(self, play_time=0):
        """Create a record."""
        self.play_time = play_time

table_names[Movie.__tablename__] = Movie    
