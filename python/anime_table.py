#!/usr/bin/env python3
"""anime_table module."""
from sqlalchemy import Column, Integer, String, Date, Text
from media import Media

class Anime(Media):
    """Anime table."""

    __tablename__ = "Anime"
    episodes = Column(Integer)
    end_date = Column(Date)

    def __init__(self, episodes=0, end_date=None):
        """Create a record."""
        self.episodes = episodes
        self.end_date = end_date
        
