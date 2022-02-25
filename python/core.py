#!/usr/bin/env python3
"""core module."""
from sqlalchemy import create_engine
from sqlalchemy import MetaData

engine = create_engine("sqlite+pysqlite:///:memory:", echo=True, future=True)
meta = MetaData()
