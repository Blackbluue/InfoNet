#!/usr/bin/env python3
"""anime_table module."""
from sqlalchemy import Column, Integer, Date, ForeignKey
from python.media_table import Media, table_names

class Anime(Media):
    """Anime table."""

    __tablename__ = "Anime"
    id = Column(Integer, ForeignKey('Media.id'), primary_key=True)
    episodes = Column(Integer)
    end_date = Column(Date)

    __mapper_args__ = {
        'polymorphic_identity':'anime',
    } 

table_names[Anime.__tablename__] = Anime  
