#!/usr/bin/env python3
"""core module."""
from sqlalchemy import create_engine
from sqlalchemy.orm import Session
from media_table import Base

engine = create_engine("sqlite+pysqlite:///:memory:", echo=True, future=True)

def add_row():
    with Session(engine) as session:
        # code to add a row to table
        session.commit()
def remove_row():
    pass
def change_row():
    pass
def empty_table():
    pass

