#!/usr/bin/env python3
"""media module."""
from sqlalchemy import Column, Integer, String, Date, Text
from media import Media

class Anime(Media):
    """Anime table."""

    __tablename__ = "Anime"

    def __init__(self):
        """Create a record."""
        
